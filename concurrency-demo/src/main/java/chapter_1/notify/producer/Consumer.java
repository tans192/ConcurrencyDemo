package chapter_1.notify.producer;

import java.util.List;

/**
 * @author xiang.wei
 * @date 2019/11/15 9:38
 */
public class Consumer implements Runnable {
    private final List<Integer> taskQueue;

    public Consumer(List<Integer> taskQueue) {
        this.taskQueue = taskQueue;
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
        synchronized (taskQueue) {
            while (taskQueue.isEmpty()) {
                System.out.println("**********队列为空,当前" + Thread.currentThread().getName() + " 正在等待 , 大小: " + taskQueue.size());
                taskQueue.wait();
            }
            Thread.sleep(1000);
            Integer i = taskQueue.remove(0);
            System.out.println("******消费了" + i);
            taskQueue.notifyAll();
        }
    }
}
