package chapter_1.reentrantLock;

/**
 * @author xiang.wei
 * @date 2019/11/1 13:26
 */
public class LockTest {

    public static void main(String[] args) {
        MyThreadService myThreadService = new MyThreadService();
        for (int i = 0; i < 5; i++) {
            MyThread myThread = new MyThread("线程" + i, myThreadService);
            myThread.start();
        }
    }

    static class MyThread extends Thread {
        MyThreadService myThreadService = null;

        public MyThread(String name, MyThreadService myThreadService) {
            super(name);
            this.myThreadService = myThreadService;
        }

        @Override
        public void run() {
            myThreadService.printThread();
        }
    }
}
