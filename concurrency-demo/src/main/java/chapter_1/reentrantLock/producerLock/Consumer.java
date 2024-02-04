package chapter_1.reentrantLock.producerLock;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author xiang.wei
 * @date 2019/11/15 9:38
 */
public class Consumer implements Runnable {
    private final List<Integer> taskQueue;
    private final Lock lock;
    private Condition notFull;
    private Condition notEmpty;

    public Consumer(List<Integer> taskQueue, Lock lock, Condition notFull, Condition notEmpty) {
        this.taskQueue = taskQueue;
        this.lock = lock;
        this.notFull = notFull;
        this.notEmpty = notEmpty;
    }

    public void run() {
        while (true) {
            try {
                cusume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 消费
     */
    private void cusume() throws InterruptedException {
        lock.lock();
        try {
            while (taskQueue.isEmpty()) {
                System.out.println("**********队列为空,当前" + Thread.currentThread().getName() + " 正在等待 , 大小: " + taskQueue.size());
                notEmpty.await();
            }
            Thread.sleep(1000);
            Integer i = taskQueue.remove(0);
            System.out.println("******消费了" + i);
            notFull.signalAll();

        } finally {
            lock.unlock();
        }

    }
}
