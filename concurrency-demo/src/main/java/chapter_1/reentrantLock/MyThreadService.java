package chapter_1.reentrantLock;

import java.util.Collections;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiang.wei
 * @date 2019/11/5 18:43
 */
public class MyThreadService {
    final Lock lock = new ReentrantLock();


    public void printThread() {
        lock.lock();
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("*******" + Thread.currentThread().getName() + (" " + i));
            }
        } finally {
            lock.unlock();
        }
    }
}
