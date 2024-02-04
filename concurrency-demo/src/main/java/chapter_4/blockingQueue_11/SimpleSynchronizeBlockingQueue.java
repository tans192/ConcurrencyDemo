package chapter_4.blockingQueue_11;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiang.wei
 * @date 2020/2/7 9:57 AM
 */
public class SimpleSynchronizeBlockingQueue {
    /**
     * 存放数据的数组
     */
    private volatile Object[] items;
    /**
     * 弹出的元素的下标
     */
    private volatile int takeIndex;
    /**
     * 插入元素的下标
     */
    private volatile int putIndex;
    /**
     * 数组中元素的大小
     */
    private volatile int count;

    /**
     * @param capacity
     */
    public SimpleSynchronizeBlockingQueue(int capacity) {
        items = new Object[capacity];
        takeIndex = putIndex = count = 0;
    }

    /**
     * 入队操作（插入元素）
     *
     * @param element
     * @return
     */
    public synchronized boolean enqueue(String element) throws InterruptedException {
        //判断是否队满
        try {
            while (count == items.length) {
                System.out.println("**********队列已满****");
                this.wait();
            }
            System.out.println("*******"+Thread.currentThread().getName()+"在"+System.currentTimeMillis()+"时入队的元素是="+element);
            //插入元素
            items[putIndex] = element;
            //putIndex向后移动一位，如果已经到达了末尾则会返回队列开头
            if (++putIndex == items.length) {
                putIndex = 0;
            }
            count++;
            this.notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 出队操作（弹出元素）
     *
     * @return
     */
    public synchronized Object dequeue() throws InterruptedException {
        Object item = null;
        //判断队列是否为空
        try {
            while (count == 0) {
                System.out.println("***********队列为空********");
                this.wait();
            }
            //取出takeIndex指向位置中的元素
            item = items[takeIndex];
            //并将该位置清空
            items[takeIndex] = null;
            //takeInde向后移动一位，如果已经到达了末尾则会返回队列开头
            if (++takeIndex == items.length) {
                takeIndex = 0;
            }
            count--;
            this.notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static void main(String[] args) throws InterruptedException {
        //创建大小为4的阻塞队列
        final SimpleSynchronizeBlockingQueue simpleBlockingQueue = new SimpleSynchronizeBlockingQueue(4);
        //创建两个线程
        final int threads = 2;
        //每个线程执行10次
        final int times = 10;
        //线程列表
        List<Thread> threadList = new ArrayList<>(threads * 2);
        long startTime = System.currentTimeMillis();
        //创建2个生产者线程，向队列中并发放入数字0到9，每个线程放入10个数字
        for (int i = 0; i < threads; ++i) {
            final int offset = i * times;
            Thread producer = new Thread(() -> {
                try {
                    for (int j = 0; j < times; ++j) {
                        simpleBlockingQueue.enqueue(String.valueOf(offset + j));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            threadList.add(producer);
            producer.start();
        }

        // 创建2个消费者线程，从队列中弹出20次数字并打印弹出的数字
        for (int i = 0; i < threads; ++i) {
            Thread consumer = new Thread(() -> {
                try {
                    for (int j = 0; j < times; ++j) {
                        String element =(String) simpleBlockingQueue.dequeue();
                        System.out.println(Thread.currentThread().getName()+"在"+System.currentTimeMillis()+"时"+"取出的元素="+element);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            threadList.add(consumer);
            consumer.start();
        }

        // 等待所有线程执行完成
        for (Thread thread : threadList) {
            thread.join();
        }
        for (int i = 0; i < simpleBlockingQueue.items.length; i++) {
            System.out.println("*********队列中剩余的元素="+simpleBlockingQueue.items[i]);
        }
        // 打印运行耗时
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("总耗时：%.2fs", (endTime - startTime) / 1e3));
    }
}
