package chapter_3;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * JDK1.7 版本
 * @author xiang.wei
 * @date 2019/12/24 15:43
 */
public class HashMapThreadTest3 {
    /**
     * 定义大小为4的map
     */
    private static Map<Integer, Integer> paramMap = new HashMap<>(2);

    public static void main(String[] args) throws InterruptedException {
        /**
         * 计数器
         */
        final CountDownLatch countDownLatch = new CountDownLatch(6);

        final AtomicInteger atomicInteger = new AtomicInteger(0);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                int andGet = 0;
                while (andGet < 100000) {
                    paramMap.put(andGet, andGet);
                    andGet = atomicInteger.incrementAndGet();
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        for (Integer key : paramMap.keySet()) {
            System.out.println("******key:" + key + ",value:" + paramMap.get(key));
        }
    }
}
