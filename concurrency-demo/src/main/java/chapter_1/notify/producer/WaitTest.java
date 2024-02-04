package chapter_1.notify.producer;

/**
 * @author xiang.wei
 * @date 2019/11/15 14:27
 */
public class WaitTest {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new sleepRunner(), "线程1");
        Thread thread2 = new Thread(new sleepRunner(), "线程2");
        thread1.start();
        thread2.start();
    }

    static class sleepRunner implements Runnable {
        public void run() {
            synchronized (WaitTest.class) {
                try {
                    long startTime = System.currentTimeMillis();
                    System.out.println("***********" + Thread.currentThread().getName() + "开始等待");
                    WaitTest.class.wait(1000);
                    long time = System.currentTimeMillis() - startTime;
                    System.out.println("***********" + Thread.currentThread().getName() + "花费"+time+"毫秒，结束睡眠");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
