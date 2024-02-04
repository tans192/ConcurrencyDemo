package chapter_1.notify.producer;

/**
 * sleeep休眠，释放CPU的执行权限
 * @author xiang.wei
 * @date 2019/11/15 14:15
 */
public class SleepTest {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new SleepRunner(), "线程1");
        Thread thread2 = new Thread(new SleepRunner(), "线程2");
        thread1.start();
        thread2.start();
    }

    static class SleepRunner implements Runnable {
        public void run() {
            synchronized (SleepRunner.class) {
                try {
                    System.out.println("***********" + Thread.currentThread().getName() + "开始睡眠");
                    Thread.sleep(1000);
                    System.out.println("***********" + Thread.currentThread().getName() + "结束睡眠");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
