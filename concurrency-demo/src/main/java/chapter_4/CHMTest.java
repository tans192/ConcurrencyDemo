package chapter_4;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xiang.wei on 2020/1/29
 *
 * @author xiang.wei
 */
public class CHMTest extends Thread {

    static ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>(32);

    static AtomicInteger at = new AtomicInteger(1);

    /**
     * 线程一
     */
    private static final String THREAD1_NAME = "thread0";
    /**
     * 线程二
     */
    private static final String THREAD2_NAME = "thread1";

    @Override
    public void run() {
        if (Thread.currentThread().getName().equals(THREAD1_NAME)) {
            System.out.println("线程一启动");
            for (int i=1;i<100000;i++) {
                map.put(28 + (i << 5), 28 + (i << 5));
                map.put(21 + (i << 5), 21 + (i << 5));
            }
        } else if (Thread.currentThread().getName().equals(THREAD2_NAME)) {
            System.out.println("线程二启动");
            for (int i = 1; i < 100000; i++) {
                map.put(29 + (i << 5), 29 + (i << 5));
                map.put(22 + (i << 5), 22 + (i << 5));
            }
        }

    }

    public static void main(String[] args) {
        CHMTest t0 = new CHMTest();
        t0.setName(THREAD1_NAME);
        CHMTest t1 = new CHMTest();
        t1.setName(THREAD2_NAME);
        t0.start();
        t1.start();
    }
}
