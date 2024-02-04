package chapter_4.blockingQueue_12;

import chapter_4.blockingQueue_11.SimpleBlockingQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author xiang.wei
 * @date 2020/2/8 8:03 PM
 */
public class ArrayBlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
        //创建大小为4的阻塞队列
        final ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(4);
        //创建两个线程
        final int threads = 4;
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
                        arrayBlockingQueue.put(String.valueOf(offset + j));
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
                        String element =(String) arrayBlockingQueue.take();
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
        // 打印运行耗时
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("总耗时：%.2fs", (endTime - startTime) / 1e3));
    }
}
