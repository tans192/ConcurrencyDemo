package chapter_5;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xiang.wei
 * @date 2020/2/25 7:21 PM
 */
public class ExectorTest {
    public static void main(String[] args) {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("测试线程-%d").build();
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(
                10,
                20,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1),
                threadFactory
//                , new ThreadPoolExecutor.CallerRunsPolicy()
        );
        for (int i=0;i<20;i++) {
            executorService.execute(()->{
                System.out.println(Thread.currentThread().getName()+" 开始执行任务");
                int j = 10000 * 10000;
                while (j >0) {
                    j--;
                }

                System.out.println(Thread.currentThread().getName()+" 执行结束");

            });
        }
    }
}
