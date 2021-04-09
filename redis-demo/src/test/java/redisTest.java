import com.soda.redis.MyRedisApplication;
import com.soda.redis.lock.DistributeLock;
import com.soda.redis.pojo.Player;
import com.soda.redis.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.Conditional;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
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

    @Autowired
    @Resource
    private RedisTemplate redisTemplate;

    //redis string类型
    @Test
    public void redisTest() {
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



    //redis list类型
    //list类型实际上是一个队列
    @Test
    public void test2() {

        //从左压入
        redisTemplate.opsForList().leftPush("listTest", new User("杜甫", 20));
        redisTemplate.opsForList().leftPush("listTest", new User("王安石", 30));

        //从右边弹出
        System.out.println(redisTemplate.opsForList().rightPop("listTest"));
        System.out.println(redisTemplate.opsForList().rightPop("listTest"));

    }


    //redis hash类型
    @Test
    public void test03() {

        redisTemplate.opsForHash().put("hashTest", "user1", new User("李白", 20));
        System.out.println(redisTemplate.opsForHash().get("hashTest", "user1"));
    }

    //redis Zset类型
    @Test
    public void test4() {
        //粉丝越低排行越高
        redisTemplate.opsForZSet().add("zetTest", new User("李白", 20), 20);
        redisTemplate.opsForZSet().add("zetTest", new User("李白", 3), 3);
        redisTemplate.opsForZSet().add("zetTest", new User("李航", 30), 10);

        Iterator zetTest = redisTemplate.opsForZSet().range("zetTest", 0, 2).iterator();
        while (zetTest.hasNext()) {
            System.out.println(zetTest.next().toString());
        }
    }


    //redis set类型
    @Test
    public void test5() {

        redisTemplate.opsForSet().add("zetTest1", new User("李白", 20));
        redisTemplate.opsForSet().add("zetTest1", new User("李航", 20));
        redisTemplate.opsForSet().add("zetTest1", new User("李航", 20));

        //
        System.out.println(redisTemplate.opsForSet().pop("zetTest1").toString());
    }


    //redis bitmap类型
    @Test
    public void test6() {

        redisTemplate.opsForValue().setBit("videoLike", 7, true);
    }


    /**
     * 使用bitmap实现用户点赞视频demo
     */
    @Test
    public void test7() {

        //随机用户点赞视频最大为1000次
        //多次运行先前的设置的bit位也还是会存在的
        for (int i = 0; i < 1000; i++) {

            double d = Math.random() * 100000;
            long loc = (long) d;
            redisTemplate.opsForValue().setBit("videoLike", loc, true);
        }

        //获取视频点赞的用户数
        System.out.println(redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                //connection的bitcount方法直接接收bytes，需要先转换再传进去
                Jackson2JsonRedisSerializer<String> stringJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(String.class);
                byte[] videoLikes = stringJackson2JsonRedisSerializer.serialize("videoLike");
                return connection.bitCount(videoLikes);
            }
        }));
    }



    /**
     * 使用Zset实现实时比赛排行榜
     */
    @Test
    public void test8() throws InterruptedException {

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
                Player  next = (Player) running1.next();
                System.out.println(next);
            }
            TimeUnit.SECONDS.sleep(3);
            System.out.println("----------------------------");
        }



    }


    /**
     * player线程
     * @param player1
     */
    private void playerThread(Player player1) {
        new Thread(()->{

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
     * 多线程分布式锁测试
     */
    @Test
    public void test9() {

        Player player = new Player("李白", 0);

        for (int i = 0; i < 10; i++) {
            distributeThread(player);
        }

        while (true) {

        }


    }

    /**
     * 分布式锁测试
     */
    @Test
    public void test10() throws InterruptedException {

        DistributeLock distributeLock = new DistributeLock(redisTemplate);
        String s = UUID.randomUUID().toString();
        try {

            while (!distributeLock.lockWithLua(s)) {

            }
            System.out.println("get the lockWithLua");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            TimeUnit.SECONDS.sleep(30);
            distributeLock.unlockWithLua(s);
        }
    }

    /**
     * 测试分布式锁线程
     * @param player
     */
    private void distributeThread(Player player) {
        new Thread(()->{

            DistributeLock distributeLock = new DistributeLock(redisTemplate);
            String uuid = UUID.randomUUID().toString();
            for (int i = 0; i < 100; i++) {
                try {

                    //可以测试加不加锁区别
                    while (!distributeLock.lockWithLua(uuid));

//                    System.out.println(Thread.currentThread().getName() + "get the lockWithLua");

                    player.setDistance(player.getDistance() + 1);

                    System.out.println(player.getDistance());

                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    distributeLock.unlockWithLua(uuid);
                }

            }

        }).start();
    }

    /**
     * 注解解析
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
     * 缓存一致性
     */
    @Test
    public void test11() {
        String key = "cacheConsistency";
        redisTemplate.opsForValue().set(key, "test1", 30, TimeUnit.MINUTES);
        
    }


    /**
     * redis实现消息队列
     */
    @Test
    public void messageQueue() {

        String key = "message";
        //生产消息
        new Thread(() ->{

            for (int i = 0; i < 300; i++) {
                int index = i;
                redisTemplate.execute((RedisCallback) (connection)->{
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

            new Thread(()->{

                for (int i = 0; i < 100; i++) {
                    List execute = (List) redisTemplate.execute((RedisCallback) connection -> {

                        //阻塞式获取队列消息
                        List<byte[]> bytes = connection.listCommands().bRPop(0, key.getBytes());
                        return bytes;
                    });

                    //打印数据
                    execute.forEach( (Consumer<byte[]>) (data) ->{
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





}


