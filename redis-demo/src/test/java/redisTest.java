import com.soda.redis.MyRedisApplication;
import com.soda.redis.lock.DistributeLock;
import com.soda.redis.pojo.Player;
import com.soda.redis.pojo.User;
import javafx.geometry.Bounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.Conditional;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyRedisApplication.class)
public class redisTest {

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    //redis string类型
    @Test
    public void stringOperation() {
        User user = new User("李白", 18);

        System.out.println(redisTemplate.getKeySerializer());
        System.out.println(redisTemplate);
        System.out.println(redisTemplate.getConnectionFactory());

        redisTemplate.opsForValue().set("myRedis1", user);
        //设置过期时间
        redisTemplate.opsForValue().getOperations().expire("myRedis1", 100, TimeUnit.SECONDS);

        System.out.println(redisTemplate.opsForValue().get("myRedis1"));


        //测试反序列化

/*
        Animal animal = new Animal("企鹅", 3);
        animal.setUser(user);
        redisTemplate.opsForValue().set("animal", animal);

        System.out.println(redisTemplate.opsForValue().get("animal"));
*/
    }


    /**
     * redis list类型
     * list类型实际上是一个队列
     */
    @Test
    public void listOperation() {

        //从左压入
        redisTemplate.opsForList().leftPush("listTest", new User("杜甫", 20));
        redisTemplate.opsForList().leftPush("listTest", new User("王安石", 30));

        //从右边弹出
        System.out.println(redisTemplate.opsForList().rightPop("listTest"));
        System.out.println(redisTemplate.opsForList().rightPop("listTest"));

        //存map进list
        Map<String, Object> map = new HashMap<>();
        map.put("acid", "123123");
        String cntt = "中国证\"中国\" ";
        System.out.println(cntt);
        map.put("cntt", cntt);
        redisTemplate.opsForList().leftPush("mapList", map);
        Map<String, Object> mapList = (Map<String, Object>) redisTemplate.opsForList().rightPop("mapList");
        mapList.forEach((key, str) -> {
            System.out.println(str);
        });
    }


    /**
     * redis hash类型
     */
    @Test
    public void hashOperation() {

        redisTemplate.opsForHash().put("hashTest", "user1", new User("李白", 20));
        System.out.println(redisTemplate.opsForHash().get("hashTest", "user1"));
    }

    /**
     * redis Zset类型
     */
    @Test
    public void zSetOperation() {
        //粉丝越低排行越高
        redisTemplate.opsForZSet().add("zetTest", new User("李白", 20), 20);
        redisTemplate.opsForZSet().add("zetTest", new User("李白", 3), 3);
        redisTemplate.opsForZSet().add("zetTest", new User("李航", 30), 10);

        Iterator zetTest = redisTemplate.opsForZSet().range("zetTest", 0, 2).iterator();
        while (zetTest.hasNext()) {
            System.out.println(zetTest.next().toString());
        }
    }


    /**
     * redis set类型
     */
    @Test
    public void setOperation() {

        redisTemplate.opsForSet().add("zetTest1", new User("李白", 20));
        redisTemplate.opsForSet().add("zetTest1", new User("李航", 20));
        redisTemplate.opsForSet().add("zetTest1", new User("李航", 20));
        //
        System.out.println(redisTemplate.opsForSet().pop("zetTest1").toString());
    }

    /**
     * redis bitmap类型
     */
    @Test
    public void bitOperation() {
        byte[] keyBytes = "w".getBytes();
        redisTemplate.opsForValue().set("w", "hello");
        redisTemplate.opsForValue().setBit("w", 7, true);
        System.out.println(redisTemplate.opsForValue().getBit("w", 7));
        // bitCount template没有相关操作，只能使用execute
        // range表示的是字节范围， redis 7.0版本才可以指定bit范围
        Long count = (Long) redisTemplate.execute((RedisCallback<Long>)
                connection -> connection.bitCount(keyBytes, 0L, 8L));
        System.out.println(count);
        // range：表示的是字节范围
        Range<Long> to = Range.from(Range.Bound.inclusive(0L)).to(Range.Bound.inclusive(1L));
        Long posCount = (Long) redisTemplate.execute((RedisCallback<Long>) conn -> conn.bitPos(keyBytes, true, to));
        System.out.println(posCount);

    }

    /**
     * 使用Zset实现实时比赛排行榜
     */
    @Test
    public void matchRank() throws InterruptedException {

        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Player.class));

        Player player1 = new Player("小李", 0);
        Player player2 = new Player("小王", 0);
        Player player3 = new Player("小张", 0);
        Player player4 = new Player("小苏", 0);

        //预设分数
        redisTemplate.opsForZSet().add("running", player1, 10000);
        redisTemplate.opsForZSet().add("running", player2, 10000);
        redisTemplate.opsForZSet().add("running", player3, 10000);
        redisTemplate.opsForZSet().add("running", player4, 10000);

        //线程开启
        playerThread(player1);
        playerThread(player2);
        playerThread(player3);
        playerThread(player4);

        //在测试环境中，如果测试线程结束了子线程也就会被销毁，和main线程有区别，因此测试线程应该等到子线程结束之后才停止
        //打印排行名次
        while (true) {

            Iterator running1 = redisTemplate.opsForZSet().range("running", 0, 3).iterator();
            while (running1.hasNext()) {
                Player next = (Player) running1.next();
                System.out.println(next);
            }
            TimeUnit.SECONDS.sleep(3);
            System.out.println("----------------------------");
        }


    }


    /**
     * player线程
     *
     * @param player1
     */
    private void playerThread(Player player1) {
        new Thread(() -> {

            double score = redisTemplate.opsForZSet().score("running", player1);
            while (score > 0) {

                score -= Math.random() * 1000;
                if (score < 0) {
                    System.out.println(player1.getName() + "end:" + score);
                }
                redisTemplate.opsForZSet().add("running", player1, score);

                try {
                    TimeUnit.SECONDS.sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(player1.getName() + ":" + score);
        }).start();
    }

    /**
     * 分布式锁测试
     */
    @Test
    public void distributeLockTest() throws InterruptedException {

        DistributeLock distributeLock = new DistributeLock(redisTemplate);
        String s = UUID.randomUUID().toString();
        try {

            while (!distributeLock.lockWithLua(s)) {

            }
            System.out.println("get the lockWithLua");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            TimeUnit.SECONDS.sleep(30);
            distributeLock.unlockWithLua(s);
        }
    }

    /**
     * 测试分布式锁线程
     *
     * @param player
     */
    private void distributeThread(Player player) {
        new Thread(() -> {

            DistributeLock distributeLock = new DistributeLock(redisTemplate);
            String uuid = UUID.randomUUID().toString();
            for (int i = 0; i < 100; i++) {
                try {

                    //可以测试加不加锁区别
                    while (!distributeLock.lockWithLua(uuid)) ;

//                    System.out.println(Thread.currentThread().getName() + "get the lockWithLua");

                    player.setDistance(player.getDistance() + 1);

                    System.out.println(player.getDistance());

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    distributeLock.unlockWithLua(uuid);
                }

            }

        }).start();
    }

    /**
     * 注解解析
     *
     * @throws ClassNotFoundException
     */
    @Test
    public void test3() throws ClassNotFoundException {
        Class<?> aClass = Class.forName("org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean");
        Annotation[] annotations = aClass.getAnnotations();
        Conditional annotation = aClass.getAnnotation(Conditional.class);
        System.out.println(annotations.length);
        for (int i = 0; i < annotations.length; i++) {
            System.out.println(annotations[i].annotationType());

        }
        Class<? extends Condition>[] value = annotation.value();
        for (int i = 0; i < value.length; i++) {
            System.out.println(value[i]);
        }
    }


    /**
     * redis实现消息队列
     */
    @Test
    public void messageQueue() {

        String key = "message";
        //生产消息
        new Thread(() -> {

            for (int i = 0; i < 300; i++) {
                int index = i;
                redisTemplate.execute((RedisCallback) (connection) -> {
                    //定时发送，测试消费端是否永久阻塞
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    connection.listCommands().lPush(key.getBytes(), ("message" + index).getBytes());
                    return null;
                });
            }
        }).start();

        //3个线程消费消息
        for (int i1 = 0; i1 < 3; i1++) {

            new Thread(() -> {

                for (int i = 0; i < 100; i++) {
                    List execute = (List) redisTemplate.execute((RedisCallback) connection -> {

                        //阻塞式获取队列消息
                        List<byte[]> bytes = connection.listCommands().bRPop(0, key.getBytes());
                        return bytes;
                    });

                    //打印数据
                    execute.forEach((Consumer<byte[]>) (data) -> {
                        String s = new String(data);
                        if (!s.equals(key)) {
                            System.out.println(s);
                        }
                    });
                }
            }, "thread" + i1).start();
        }


        while (true) {

        }
    }

    /**
     * redis实现共同关注
     */
    @Test
    public void commonFocus() {

        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));

        String key = "小张";
        String key1 = "小李";

        //小张关注的
        redisTemplate.opsForSet().add(key, "手机");
        redisTemplate.opsForSet().add(key, "漫画");
        redisTemplate.opsForSet().add(key, "电脑");
        redisTemplate.opsForSet().add(key, "美女");

        //小李关注的
        redisTemplate.opsForSet().add(key1, "读书");
        redisTemplate.opsForSet().add(key1, "漫画");
        redisTemplate.opsForSet().add(key1, "音乐");
        redisTemplate.opsForSet().add(key1, "美女");

        //获取共同关注
        Set intersect = redisTemplate.opsForSet().intersect(key, key1);

        Set difference = redisTemplate.opsForSet().difference(key, key1);

        System.out.println("common:");
        intersect.iterator().forEachRemaining((data) -> {
            System.out.println(data);
        });


        System.out.println("小王 diff with 小李：");
        difference.iterator().forEachRemaining((data) -> {
            System.out.println(data);
        });
    }


    /**
     * bitField操作
     */
    @Test
    public void bitFieldCooperation() {
        //redisTemplate.
        redisTemplate.opsForValue().set("w", "hello");
        BitFieldSubCommands bitFieldSubCommands = BitFieldSubCommands.create();
        // get操作，从第二位开始，连续取8个位
        List w = redisTemplate.opsForValue().bitField("w", bitFieldSubCommands.get(BitFieldSubCommands.BitFieldType.UINT_8)
                .valueAt(2));
        System.out.println(w.get(0));
        // set操作，从第8位开始，将连续的8位替换成97
        redisTemplate.opsForValue().bitField("w", bitFieldSubCommands.set(BitFieldSubCommands.BitFieldType.UINT_8)
                .valueAt(8).to(97));
        Object w1 = redisTemplate.opsForValue().get("w");
        System.out.println(w1);
        //incrby操作 从第8位开始连续取8位并加1，如果溢出就操作失败。如果没有设置overflow，那么对于unsigned类型，溢出会置0,
        // signed类型溢出会转换符号（比如127溢出会变成-128）
        redisTemplate.opsForValue().set("w", "hello");
        List w2 = redisTemplate.opsForValue().bitField("w", bitFieldSubCommands.incr(BitFieldSubCommands.BitFieldType.UINT_8)
                .valueAt(8).overflow(BitFieldSubCommands.BitFieldIncrBy.Overflow.FAIL).by(1L));
        System.out.println(w2.get(0));
    }


    /**
     * 模拟bit签到
     */
    @Test
    public void mockSignIn() {
        String key = "mockSignIn";
        for (int i = 0; i < 365; i++) {
            double random = Math.random();
            boolean b = random > 0.5 ? true : false;
            redisTemplate.opsForValue().setBit(key, i, b);
        }
        int start = 0;
        int end = 365 % 8 != 0 ? 365 / 8 + 1 : 365 / 8;
        Long count = (Long) redisTemplate.execute((RedisCallback<Long>) conn -> conn.bitCount(key.getBytes(), start, end));
        System.out.println("一年共签到：" + count);
        Calendar instance = Calendar.getInstance();
        Long start1 = 0L;
        Long end1 = (long) (31 % 8 != 0 ? 31 / 8 + 1 : 31 / 8) - 1;
        byte[] bytes = (byte[]) redisTemplate.execute((RedisCallback<byte[]>) conn -> conn.getRange(key.getBytes(), start1, end1));
        //统计签到
        int bitCount = 0;
        int countNum = 0;
        for (byte aByte : bytes) {
            if (countNum + 8 > 31) {
                int diff = 8 - (31 - countNum);
                aByte = (byte) (aByte  & ((0xff << diff) & 0xff));
            }
            countNum += 8;

            bitCount += Integer.bitCount(aByte & 0xff);
        }
        System.out.println("1月份签到：" + bitCount);
    }

    @Test
    public void insertKey() {
        for (int i = 1000; i < 10000; i++) {
            redisTemplate.opsForValue().set("key" + i, i);
        }
    }
}


