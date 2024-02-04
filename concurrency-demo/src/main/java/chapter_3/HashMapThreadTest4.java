package chapter_3;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * JDK1.7 多线程下扩容
 *
 * @author xiang.wei
 * @date 2019/12/24 16:22
 */
public class HashMapThreadTest4 {

    private static Map<Integer, Integer> map = new HashMap<>(2);

    public static void main(String[] args) throws InterruptedException {
        //并发计数器
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        map.put(3, 3);
        new Thread(()->{
            map.put(5, 5);
            countDownLatch.countDown();}).start();

        new Thread(() -> {
                    map.put(4, 4);
                    countDownLatch.countDown();
                }
        ).start();
        countDownLatch.await();
        for (Integer key : map.keySet()) {
            System.out.println("key:" + key + "value:" + map.get(key));
        }
    }
}
