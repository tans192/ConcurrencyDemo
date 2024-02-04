package chapter_1.reentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiang.wei
 * @date 2019/11/5 16:16
 */
public class ReentryLockTest {
    final Lock lock = new ReentrantLock();
    int value;

    public int getValue() {
        lock.lock();
        try {
            System.out.println("********" + Thread.currentThread().getName() + "读取到的值是" + value);
            return value;
        } finally {
            lock.unlock();
        }
    }

    public void setValue() {
        lock.lock();
        try {
            //在此处如果锁不能重入，则会发生阻塞。如果可以重入则可以加锁成功。
            value = getValue() + 100;
            System.out.println("********" + Thread.currentThread().getName() + "设置的值是" + value);
        } finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) {
        final ReentryLockTest myThreadService = new ReentryLockTest();
        for (int i = 0; i < 5; i++) {
            Thread myThread = new Thread(new Runnable() {
                public void run() {
                    myThreadService.setValue();
                    myThreadService.getValue();
                }
            });
            myThread.start();
        }
    }

}
