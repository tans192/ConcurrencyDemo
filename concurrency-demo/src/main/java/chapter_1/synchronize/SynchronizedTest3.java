package chapter_1.synchronize;

/**
 * @author xiang.wei
 * @date 2019/10/30 18:56
 */
public class SynchronizedTest3 {
    int a = 0;
    int b = 0;
    static int c = 0;

    /**
     * 锁定的是this对象，保护了,a,b两个资源
     */
    synchronized void setValue() {
        a = 100;
        b = 20;
    }

    /**
     * 锁定的是SynchronizedTest3的class对象，
     * 保护了资源c
     * @return
     */
    synchronized static int getValue() {
        return SynchronizedTest3.c;
    }

}
