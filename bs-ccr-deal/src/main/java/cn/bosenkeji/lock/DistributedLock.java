package cn.bosenkeji.lock;

/**
 * @author CAJR
 * @date 2019/12/10 2:33 下午
 */
public interface DistributedLock {
    /**
     * 获取锁
     * @return 锁标识
     */
    String acquireLock();

    /**
     * 释放锁
     * @param lockLogo 锁标识
     * @return
     */
    boolean releaseLock(String lockLogo);
}
