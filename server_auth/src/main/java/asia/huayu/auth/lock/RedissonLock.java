package asia.huayu.auth.lock;

import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author RainZiYu
 * @Date 2023/3/22
 */
@Component
public class RedissonLock {
    @Autowired
    RedissonClient redissonClient;

    public ReadWriteLock getReadWriteLock(String lockKey) {
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock(lockKey);
        return readWriteLock;
    }
}
