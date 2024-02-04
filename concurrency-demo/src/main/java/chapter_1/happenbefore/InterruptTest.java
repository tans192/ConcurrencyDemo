package chapter_1.happenbefore;

/**
 * @author xiang.wei
 * @date 2019/10/29 14:04
 */
public class InterruptTest {
    public void interruptTest() {

    }
    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread("线程A");
        threadA.start();
        threadA.interrupt();
        System.out.println("线程A是否终止"+threadA.isInterrupted());
    }
}
