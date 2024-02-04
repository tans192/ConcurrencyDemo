package chapter_6;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * @author xiang.wei
 * @date 2020/6/17 10:29
 */
public class MessageExecutorTest {
    public static void main(String[] args) {
        //创建线程池的方式
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("测试-%s").build();
        ExecutorService executorService = newFixedThreadPool(4, threadFactory);
    }
}
