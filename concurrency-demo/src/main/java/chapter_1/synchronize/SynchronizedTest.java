package chapter_1.synchronize;

/**
 * @author xiang.wei
 * @date 2019/10/29 19:37
 */
public class SynchronizedTest {
    private int a = 0;

    public synchronized  void test1() {
        System.out.println("*******test1执行");
    }
    public void test2() {
        synchronized (this) {
            System.out.println("*******test2执行");
        }
    }
    public synchronized static void test3() {
        System.out.println("*******test3执行");
    }

    public static void main(String[] args) {
        SynchronizedTest synchronizedTest = new SynchronizedTest();
        synchronizedTest.test1();
        synchronizedTest.test2();

    }
}
