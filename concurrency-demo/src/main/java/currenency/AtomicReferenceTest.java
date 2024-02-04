package currenency;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author xiang.wei
 * @date 2020/1/19 19:17
 */
public class AtomicReferenceTest {
    private AtomicReference atest = new AtomicReference(new BigDecimal(0));


    public  void countTest() {
        for (int i = 0; i < 100000; i++) {
            atest.getAndSet(i);
        }
    }
    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        final AtomicReferenceTest longTest = new AtomicReferenceTest();
        Thread threadA = new Thread(() -> longTest.countTest());
        Thread threadB = new Thread(() -> longTest.countTest());
        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();
        long time = System.currentTimeMillis() - startTime;
        System.out.println("*******耗时="+time+"获得到的atest值为=" + longTest.atest);

    }
}
