package chapter_1.synchronize;

/**
 * addOne()方法锁定的是SynchronizedDemo2的class对象
 * getValue()方法锁定的是实例对象this， 两把锁不互斥。
 * @author xiang.wei
 * @date 2019/10/10 19:02
 */
public class SynchronizedTest2 {
    static int value = 0;

    synchronized int getValue() {
        return value;
    }

    synchronized static void addOne() {
        value += 1;
    }


}
