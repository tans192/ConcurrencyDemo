package chapter_1.deadlock;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author xiang.wei
 * @date 2019/10/31 18:41
 */
public class DeadLockDealTest3 {
    public static void main(String[] args) {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("线程-%d").build();
        ExecutorService executorService = Executors.newCachedThreadPool(threadFactory);
        int sum = 5;
        Chopsticks[] chopsticks = new Chopsticks[sum];
        for (int i = 0; i < sum; i++) {
            chopsticks[i] = new Chopsticks(i);
        }
        for (int i = 0; i < sum; i++) {
            executorService.execute(new Philosopher(chopsticks[i], chopsticks[(i + 1) % sum]));
        }
    }

    /**
     * 筷子
     */
    static class Chopsticks {
        private int id;

        public Chopsticks(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    /**
     * 哲学家
     */
    static class Philosopher implements Runnable {
        private Chopsticks left;
        private Chopsticks right;

        public Philosopher(Chopsticks left, Chopsticks right) {
            if (left.getId() > right.getId()) {
                this.left = right;
                this.right = left;
            } else {
                this.left = left;
                this.right = right;
            }
        }

        @Override
        public void run() {
            try {
                System.out.println("********"+Thread.currentThread().getName()+"开始思考");
                //思考一段时间
                Thread.sleep(1000);
                System.out.println("********"+Thread.currentThread().getName()+"思考完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (left) {
                try {
                    //拿到左边的筷子之后等待一段时间
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (right) {
                    try {
                        System.out.println("********"+Thread.currentThread().getName()+"开始吃饭");
                        Thread.sleep(1000);
                        System.out.println("********"+Thread.currentThread().getName()+"吃完饭");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
