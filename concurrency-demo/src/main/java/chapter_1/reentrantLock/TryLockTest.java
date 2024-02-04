package chapter_1.reentrantLock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xiang.wei on 2019/11/17
 *
 * @author xiang.wei
 */
public class TryLockTest {
    private ArrayList<Integer> arrayList = new ArrayList<>();
    // 注意这个地方：lock 被声明为成员变量
    private Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        final TryLockTest tryLockTest = new TryLockTest();

        new Thread("A") {
            public void run() {
                tryLockTest.insert(Thread.currentThread());
            };
        }.start();

        new Thread("B") {
            public void run() {
                tryLockTest.insert(Thread.currentThread());
            };
        }.start();
    }

    public void insert(Thread thread) {
        // 使用 tryLock()
        if (lock.tryLock()) {
            try {
                System.out.println("线程" + thread.getName() + "得到了锁...");
                Thread.sleep(1000);
            } catch (Exception e) {

            } finally {
                System.out.println("线程" + thread.getName() + "释放了锁...");
                lock.unlock();
            }
        } else {
            System.out.println("线程" + thread.getName() + "获取锁失败...");
        }
    }
}