package chapter_1.synchronize;

/**
 * @author xiang.wei
 * @date 2019/11/14 16:48
 */
public class NoSafeSynchronizedTest1 {
    Long value = 0L;

    Long getValue() {
        return value;
    }

    void addThousand() {
        synchronized (value) {
            for (int i = 0; i < 10000; i++) {
                System.out.println("*********本次由" + Thread.currentThread().getName() + "执行,value="+value);
                value += 1;
            }
            System.out.println("*********当前由" + Thread.currentThread().getName() + "执行结束");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final NoSafeSynchronizedTest1 noSafeTest1 = new NoSafeSynchronizedTest1();
        Thread threadA = new Thread(new Runnable() {
            public void run() {
                noSafeTest1.addThousand();
            }
        }, "线程A");
        Thread threadB = new Thread(new Runnable() {
            public void run() {
                noSafeTest1.addThousand();
            }
        }, "线程B");
        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();
        System.out.println("**********计算得到的结果=" + noSafeTest1.getValue());
    }
}
