package chapter_1.cas;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author xiang.wei
 * @date 2019/11/16 13:55
 */
public class AtomicLongTest {
    private AtomicLong atest = new AtomicLong(0);


    public  void countTest() {
        for (int i = 0; i < 100000; i++) {
            atest.getAndIncrement();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        final AtomicLongTest longTest = new AtomicLongTest();
        Thread threadA = new Thread(new Runnable() {
            public void run() {
                longTest.countTest();
            }
        });
        Thread threadB = new Thread(new Runnable() {
            public void run() {
                longTest.countTest();
            }
        });
        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();
        long time = System.currentTimeMillis() - startTime;
        System.out.println("*******耗时="+time+"获得到的atest值为=" + longTest.atest);
    }
}
