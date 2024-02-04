package chapter_1.notify.producer;

/**
 * @author xiang.wei
 * @date 2019/11/15 14:27
 */
public class WaitTest2 {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new waitRunner(), "线程1");
        Thread thread3 = new Thread(new notifyRunner(), "线程3");
        thread1.start();
        thread3.start();

    }

    static class waitRunner implements Runnable {
        public void run() {
            synchronized (WaitTest2.class) {
                try {
                    long startTime = System.currentTimeMillis();
                    System.out.println("***********" + Thread.currentThread().getName() + "开始等待");
                    WaitTest2.class.wait(1000);
                    long time = System.currentTimeMillis() - startTime;
                    System.out.println("***********" + Thread.currentThread().getName() + "花费"+time+"毫秒，结束睡眠");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    static class notifyRunner implements Runnable {

        @Override
        public void run() {
            synchronized (WaitTest2.class) {
                WaitTest2.class.notify();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("*********"+ Thread.currentThread().getName() + "调用了唤醒的方法");
            }
        }
    }
}
