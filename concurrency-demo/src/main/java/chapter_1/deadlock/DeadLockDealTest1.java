package chapter_1.deadlock;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author xiang.wei
 * @date 2019/11/15 14:59
 */
public class DeadLockDealTest1 {
    public static void main(String[] args) {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("线程-%d").build();
        ExecutorService executorService = Executors.newCachedThreadPool(threadFactory);
        int sum = 5;
       Chopsticks[] chopsticks = new Chopsticks[sum];
        for (int i = 0; i < sum; i++) {
            chopsticks[i] = new Chopsticks();
        }
        for (int i = 0; i < sum; i++) {
            executorService.execute(new Philospher(chopsticks[i], chopsticks[(i + 1) % sum]));
        }
    }

    /**
     * 筷子
     */
    static class Chopsticks {

    }


    static class Philospher implements Runnable {
        private Chopsticks left;
        private Chopsticks right;

        public Philospher(Chopsticks left, Chopsticks right) {
            this.left = left;
            this.right = right;
        }

        public void run() {
            while (!Allocator.getAllocator().applyResource(left, right)) {
                //思考一段时间
                System.out.println("********"+Thread.currentThread().getName()+"开始思考");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("********"+Thread.currentThread().getName()+"思考结束");
                return;
            }
            synchronized (left) {
                synchronized (right) {
                    System.out.println("********"+Thread.currentThread().getName()+"开始吃饭");
                    //进餐一段时间
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("********"+Thread.currentThread().getName()+"吃完饭");
                }
            }
        }
    }
}
