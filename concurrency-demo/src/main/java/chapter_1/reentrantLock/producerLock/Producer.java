package chapter_1.reentrantLock.producerLock;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 生产者
 *
 * @author xiang.wei
 * @date 2019/11/15 9:20
 */
public class Producer implements Runnable {
    private final List<Integer> taskQueue;
    private final int MAX_CAPCITY;
    private final Lock lock;
    private Condition notFull;
    private Condition notEmpty;

    public Producer(List<Integer> taskQueue, int MAX_CAPCITY, Lock lock, Condition notFull, Condition notEmpty) {
        this.taskQueue = taskQueue;
        this.MAX_CAPCITY = MAX_CAPCITY;
        this.lock = lock;
        this.notFull = notFull;
        this.notEmpty = notEmpty;
    }

    public void run() {
        int count = 0;
        while (true) {
            try {
                producer(count++);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void producer(int i) throws InterruptedException {
        lock.lock();
        try {
            //队列已满
            while (taskQueue.size() == MAX_CAPCITY) {
                System.out.println("************队列已经满了,当前" + Thread.currentThread().getName() + " 正在等待 , 大小: " + taskQueue.size());
                notFull.await();
            }
            Thread.sleep(1000);
            taskQueue.add(i);
            System.out.println("******生产了" + i);
            notEmpty.signalAll();
        }finally {
            lock.unlock();
        }
    }

}