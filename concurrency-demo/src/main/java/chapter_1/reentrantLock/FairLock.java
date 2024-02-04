package chapter_1.reentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiang.wei
 * @date 2019/11/15 20:24
 */
public class FairLock {
    final Lock lock = new ReentrantLock();
    //创建十个线程
    public static void main(String[] args) {
        final FairLock fairLock = new FairLock();

        Runnable runnable = new Runnable() {
            public void run() {
                System.out.println("★线程" + Thread.currentThread().getName()
                        + "运行了");
                fairLock.setup();
            }
        };

        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(runnable);

        }
        for (int i=0;i<10;i++) {
            threads[i].start();
        }
    }

    //执行方法
    public void setup() {
        lock.lock();
        try {
            System.out.println("********" + Thread.currentThread().getName() + "获得了锁定");
        } finally {
            lock.unlock();
        }
    }

}


