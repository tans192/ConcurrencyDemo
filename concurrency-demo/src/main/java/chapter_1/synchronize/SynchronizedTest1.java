package chapter_1.synchronize;

/**
 * getValue()方法和addOne()方法都是锁定实例对象this的。
 * 所以线程安全
 *
 * @author xiang.wei
 * @date 2019/10/10 18:57
 */
public class SynchronizedTest1 {
    int value = 0;

    synchronized int getValue() {
        return value;
    }

    synchronized void addThousand() {
        System.out.println("*********当前线程" + Thread.currentThread().getName()+"执行开始");
        for (int i = 0; i < 600000; i++) {
            value += 1;
        }
        System.out.println("*********当前线程" + Thread.currentThread().getName()+"执行结束");
    }

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        final SynchronizedTest1 synchronizedTest1 = new SynchronizedTest1();
        Thread threadA = new Thread(new Runnable() {
            public void run() {
                synchronizedTest1.addThousand();
            }
        }, "线程A");
        Thread threadB = new Thread(new Runnable() {
            public void run() {
                synchronizedTest1.addThousand();
            }
        }, "线程B");
        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();
        long time = System.currentTimeMillis() - startTime;
        System.out.println("*******耗时="+time+"获得到的atest值为=" + synchronizedTest1.value);
    }
}
