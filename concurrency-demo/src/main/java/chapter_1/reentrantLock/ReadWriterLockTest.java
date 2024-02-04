package chapter_1.reentrantLock;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author xiang.wei
 * @date 2019/11/15 20:29
 */
public class ReadWriterLockTest {
    final ReentrantReadWriteLock readWriterLock = new ReentrantReadWriteLock();
    private Integer data = null;

    public static void main(String[] args) {
        final ReadWriterLockTest readWriterLockTest = new ReadWriterLockTest();
        for (int i = 0; i < 3; i++) {
            Thread writeThread = new Thread(new Runnable() {
                public void run() {
                    readWriterLockTest.write(new Random().nextInt(10));
                }
            }, "写线程" + i);
            writeThread.start();
        }
        for (int i = 0; i < 3; i++) {
            Thread readThread = new Thread(new Runnable() {
                public void run() {
                    readWriterLockTest.read();
                }
            }, "读线程" + i);
            readThread.start();
        }


    }

    public void read() {
        readWriterLock.readLock().lock();
        try {
            System.out.println("*****************" + Thread.currentThread().getName() + "开始读");
            Thread.sleep((long) (Math.random()*1000));
            System.out.println("***************" + Thread.currentThread().getName() + "读到数据"+data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriterLock.readLock().unlock();
        }
    }

    public void write(int data) {
        readWriterLock.writeLock().lock();
        try {
            System.out.println("*****************" + Thread.currentThread().getName() + "开始写");
            Thread.sleep((long) (Math.random()*1000));
            this.data = data;
            System.out.println("***************" + Thread.currentThread().getName() + "写入的数据" + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriterLock.writeLock().unlock();
        }
    }
}
