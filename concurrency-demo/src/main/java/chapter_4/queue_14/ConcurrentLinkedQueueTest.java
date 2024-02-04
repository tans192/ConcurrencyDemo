package chapter_4.queue_14;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author xiang.wei
 * @date 2020/2/14 11:54 AM
 */
public class ConcurrentLinkedQueueTest {
    public static void main(String[] args) {
        ConcurrentLinkedQueue<String> stringConcurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < 5; i++) {
            //入队元素
            stringConcurrentLinkedQueue.add("测试" + i);
        }
        for (int i = 0; i < 5; i++) {
            String poll = stringConcurrentLinkedQueue.poll();
            System.out.println("********出队元素:" + poll);
        }
    }
}
