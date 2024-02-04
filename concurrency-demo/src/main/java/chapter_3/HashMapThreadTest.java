package chapter_3;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程操作死循环复现
 *
 * @author xiang.wei
 * @date 2019/12/21 15:40
 */
public class HashMapThreadTest {
    /**
     * 创建一个HashMap类变量，指定数组空间为2
     */
    static ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        //控制并发原子操作
        final AtomicInteger at = new AtomicInteger(0);
        //并发计数器
        final CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
//                    int i1 = at.incrementAndGet();
//                    countDownLatch.countDown();
//                    //向map中添加元素
//                    map.put(i1, i1);
                int i1 = at.incrementAndGet();
                map.put(i1, i1);
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        for (Integer key : map.keySet()) {
            System.out.println("******key:" + key + ",value:" + map.get(key));
        }
        System.out.println("***********当前容器的大小={}" + map.size());

    }

}
