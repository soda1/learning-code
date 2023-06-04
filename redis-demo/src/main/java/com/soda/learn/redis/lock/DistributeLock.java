package com.soda.learn.redis.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 简单redis 分布式锁
 */
public class DistributeLock {


    private RedisTemplate redisTemplate;

    private final static String LockKey = "distribute_lock";

    public DistributeLock(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 使用lua加锁
     * 将value（uuid）作为线程的标识，防止线程执行时间超过key设置的过期时间后，还对key进行解锁（误解锁）
     *
     * @param uuid
     */
    public boolean lockWithLua(String uuid) {

        Object execute = redisTemplate.execute((RedisCallback<Boolean>) connection -> {

            //lua脚本加锁，防止加锁后，执行超时命令出错
            String luaScript = "if (redis.call('setnx',KEYS[1],ARGV[1]) < 1) then return 0;" +
                    " end; redis.call('expire',KEYS[1], tonumber(ARGV[2])); return 1;";

            Long eval = connection.eval(luaScript.getBytes(), ReturnType.INTEGER, 1,
                    LockKey.getBytes(), uuid.getBytes(), "40".getBytes());

            if (eval.intValue() == 1) {
                return true;
            } else {
                return false;
            }


/*
             //old version
            //使用NX选项进行加锁
            Boolean aBoolean = connection.setNX(LockKey.getBytes(), uuid.getBytes());
            if (aBoolean) {
                //设置超时自动释放锁
                connection.expire(LockKey.getBytes(), 60);
                return true;
            } else {
                return false;
            }
*/
        });


        return (boolean) execute;
    }


    /**
     * 使用lua脚本解锁，尽量保持其原子性
     *
     * @param uuid
     * @return
     */
    public Integer unlockWithLua(String uuid) {

        Long execute = (Long) redisTemplate.execute((RedisCallback<Long>) connection -> {

            //lua 脚本 https://redis.io/commands/eval
            String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                    "return redis.call('del', KEYS[1]) else return 0 end";
            return connection.eval(luaScript.getBytes(), ReturnType.INTEGER, 1, LockKey.getBytes(), uuid.getBytes());
        });

        return execute.intValue();

    }
}
