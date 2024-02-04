package chapter_1.performance;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiang.wei on 2019/11/17
 *
 * @author xiang.wei
 */
public class MapRunnable {
    //要操作的map
    private Map<Integer, String> map;
    //线程数
    private final int threadCount;
    //要写入的数据量
    private final int rw_count;

    //任务栅栏，同批任务，先到达wait的任务挂起,一直等到全部任务到达指定的wait地点后，才能全部唤醒。
    private CyclicBarrier cyclicBarrier;


    public MapRunnable(Map<Integer, String> map, int threadCount, int rw_count, CyclicBarrier cyclicBarrier) {
        this.map = map;
        this.threadCount = threadCount;
        this.rw_count = rw_count;
        this.cyclicBarrier = cyclicBarrier;
    }

    /**
     * 读任务
     */
    Runnable readrun = new Runnable() {
        @Override
        public void run() {
            for(int i=0;i<rw_count;i++){
                map.get(i);
            }
            //每个线程执行完同步方法后就等待
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    };

    Runnable writeRun = new Runnable() {
        public void run() {
             for(int i=0;i<rw_count;i++){
                 map.put(i,"测试"+rw_count);
             }
            //每个线程执行完同步方法后就等待
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    };

    public void test(String id) {
        long starttime = System.currentTimeMillis();
//        ThreadPoolExecutor se = new ThreadPoolExecutor(
//                10, threadCount,
//                1, TimeUnit.SECONDS,
//                new ArrayBlockingQueue<Runnable>(threadCount, false),
//                new ThreadPoolExecutor.CallerRunsPolicy()
//        );
//        //线程的最大空闲时间，超出这个时间将进行回收
//        se.allowCoreThreadTimeOut(true);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("测试-%d").build();
//        ExecutorService se = new ThreadPoolExecutor(10, 300, 1, TimeUnit.SECONDS,
//                new LinkedBlockingQueue<>(), threadFactory, new ThreadPoolExecutor.AbortPolicy());
//        ExecutorService se=new ThreadPoolExecutor(5, 2*threadCount,
//                1L, TimeUnit.SECONDS,
//                new SynchronousQueue<Runnable>());
        //同时开启2*threadCount个读写线程
//        for (int i = 0; i < threadCount; i++) {
//            se.execute(writeRun);
////            se.execute(readrun);
////            new Thread(writeRun,"每天的一天").start();
//        }
        map.clear();
        //所有线程都执行完成之后，才会跑到这一步
        long duration = (System.currentTimeMillis() - starttime);
        System.out.println(id+"=" + duration+"毫秒");
    }
}
