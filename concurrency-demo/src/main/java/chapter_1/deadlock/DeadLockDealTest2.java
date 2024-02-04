package chapter_1.deadlock;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiang.wei
 * @date 2019/11/15 14:48
 */
public class DeadLockDealTest2 {
    public static void main(String[] args) {
        ReentrantLock left = new ReentrantLock();
        ReentrantLock right = new ReentrantLock();
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("线程-%d").build();
        ExecutorService executorService = Executors.newCachedThreadPool(threadFactory);
        int sum = 5;
        for (int i = 0; i < sum; i++) {
            executorService.execute(new Philosopher(left, right));
        }
    }

   static class Philosopher implements Runnable{
        private ReentrantLock left,right;

        public Philosopher(ReentrantLock left, ReentrantLock right) {
            super();
            this.left = left;
            this.right = right;
        }
        public void run(){
            try {
                while(true){
                    //思考一段时间
                    System.out.println("********"+Thread.currentThread().getName()+"开始思考");
                    Thread.sleep(1000);
                    System.out.println("********"+Thread.currentThread().getName()+"思考结束");
                    left.lock();
                    try{
                        if(right.tryLock(1000, TimeUnit.MILLISECONDS)){
                            try{
                                System.out.println("********"+Thread.currentThread().getName()+"开始吃饭");
                                //进餐一段时间
                                Thread.sleep(1000);
                                System.out.println("********"+Thread.currentThread().getName()+"吃完饭");
                            }finally {
                                right.unlock();
                            }
                        }else{
                            //没有获取到右手的筷子，放弃并继续思考
                            //思考一段时间
                            System.out.println("********"+Thread.currentThread().getName()+"没有拿到右边的筷子，开始思考");
                            Thread.sleep(1000);
                            System.out.println("********"+Thread.currentThread().getName()+"没有拿到右边的筷子，思考结束");
                        }
                    }finally {
                        left.unlock();
                    }
                }
            } catch (InterruptedException e) {
            }
        }
    }

}
