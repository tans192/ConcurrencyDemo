package chapter_1.reentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiang.wei
 * @date 2019/11/5 15:29
 */
public class LockInterruptTest {
    final Lock lock = new ReentrantLock();

    public void canInterruptMessage(String message) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            //
        } finally {
            lock.unlock();
        }

    }
}
