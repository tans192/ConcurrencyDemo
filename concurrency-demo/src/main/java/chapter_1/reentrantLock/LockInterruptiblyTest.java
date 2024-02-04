package chapter_1.reentrantLock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xiang.wei on 2019/11/17
 *
 * @author xiang.wei
 */
public class LockInterruptiblyTest {
    // 注意这个地方：lock 被声明为成员变量
    private Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        final LockInterruptiblyTest lockInterruptiblyTest = new LockInterruptiblyTest();

        new Thread("A") {
            public void run() {
                try {
                    lockInterruptiblyTest.insert(Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            ;
        }.start();

        new Thread("B") {
            public void run() {
                try {
                    lockInterruptiblyTest.insert(Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            ;
        }.start();
    }

    public void insert(Thread thread) throws InterruptedException {
        //注意，如果需要正确中断等待锁的线程，必须将获取锁放在外面，然后将 InterruptedException 抛出
        lock.lockInterruptibly();
        try {
            System.out.println("线程" + thread.getName() + "得到了锁...");
            //死循环
            int i = 100;
            while (i > 0) {
                i--;
                if (i == 20) {
                    //手动抛出中断信号
                    throw new InterruptedException("手动抛出中断信号");
                }

            }
        }  finally {
            System.out.println(Thread.currentThread().getName()+"执行finally...");
            lock.unlock();
            System.out.println("线程" + thread.getName() + "释放了锁...");
        }
    }
}
