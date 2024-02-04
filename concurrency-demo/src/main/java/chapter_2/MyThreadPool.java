package chapter_2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 简化的线程池，仅用来说明工作原理
 *
 * @author xiang.wei
 * @date 2019/11/26 20:32
 */
public class MyThreadPool {
    /**
     * 阻塞队列，
     * 保存生产者-消费者模式
     */
    BlockingQueue<Runnable> workQueue;

    /**
     * 保存创建的线程
     */
    List<MyThread> myThreadList = new ArrayList<>();

    public MyThreadPool(int corePoolSize, BlockingQueue<Runnable> workQueue) {
        this.workQueue = workQueue;
        for (int i = 0; i < corePoolSize; i++) {
            MyThread myThread = new MyThread();
            System.out.println("********创建线程"+myThread.getName());
            myThread.start();
            myThreadList.add(myThread);
        }
    }

    void execute(Runnable command){
        try {
            workQueue.put(command);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     *  工作线程
     */
    class MyThread extends Thread {
        @Override
        public void run() {
            //循环取任务执行
            while (true) {
                try {
                    Runnable task = workQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
//        MyThreadPool threadPool = new MyThreadPool(10, new LinkedBlockingQueue<>());
//        threadPool.execute(() -> System.out.println("************你好，2019-11-28"));
    }

}
