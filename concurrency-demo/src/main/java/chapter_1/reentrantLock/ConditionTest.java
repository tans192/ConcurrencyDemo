package chapter_1.reentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiang.wei
 * @date 2019/11/14 20:00
 */
public class ConditionTest {
    public static void main(String[] args) throws InterruptedException {
        MyThreadConditionService myThreadConditionService = new MyThreadConditionService();
        MyThread myThread = new MyThread("线程A", myThreadConditionService);
        myThread.start();
        Thread.sleep(3000);
        myThreadConditionService.signal();
    }

    static class MyThread extends Thread {
        MyThreadConditionService myThreadConditionService = null;

        public MyThread(String name, MyThreadConditionService myThreadConditionService) {
            super(name);
            this.myThreadConditionService = myThreadConditionService;
        }

        @Override
        public void run() {
            myThreadConditionService.await();
        }
    }


    static class MyThreadConditionService {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        public void await() {
            lock.lock();
            try {
                System.out.println("await的时间是="+System.currentTimeMillis());
                //等待
                condition.await();
                System.out.println("******这是await之后的代码，signal之后才会执行");
            } catch (InterruptedException exception) {

            } finally {
                lock.unlock();
            }

        }

        public void signal() {
            lock.lock();
            try {
                System.out.println("signal的时间是="+System.currentTimeMillis());
                condition.signalAll();
                Thread.sleep(3000);
                System.out.println("**********这是signal之后的代码");
            } catch (InterruptedException exception) {

            } finally {
                lock.unlock();
            }
        }

    }
}