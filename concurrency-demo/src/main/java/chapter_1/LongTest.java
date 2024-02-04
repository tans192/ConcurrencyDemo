package chapter_1;

/**
 * @author xiang.wei
 * @date 2019/10/28 16:45
 */
public class LongTest {
    private  long atest = 0;


    public void countTest() {
        for (int i = 0; i < 10000; i++) {
            atest = atest + 1;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final LongTest longTest = new LongTest();
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
        System.out.println("*******获得到的atest值为=" + longTest.atest);
    }
}
