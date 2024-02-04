package chapter_3;

import java.util.HashMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiang.wei
 * @date 2019/12/24 9:27
 */
public class HashMapThreadTest2 {
    /**
     * 创建一个HashMap类变量，指定数组空间为2
     */
    static HashMap<Integer, Integer> map = new HashMap<>();

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        //控制并发原子操作
        final AtomicInteger at = new AtomicInteger(0);
        //栅栏
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(1000);
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    int i1 = at.incrementAndGet();
//                    countDownLatch.countDown();
//                    //向map中添加元素
//                    map.put(i1, i1);
                    int i1 = at.incrementAndGet();
                    map.put(i1, i1);
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        cyclicBarrier.await();
        for (Integer key : map.keySet()) {
            System.out.println("******key:" + key + ",value:" + map.get(key));
        }
        System.out.println("***********当前容器的大小={}" + map.size());

    }
}
