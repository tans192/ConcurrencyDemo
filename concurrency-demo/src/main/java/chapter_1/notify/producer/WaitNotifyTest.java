package chapter_1.notify.producer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiang.wei
 * @date 2019/11/15 9:51
 */
public class WaitNotifyTest {
    public static void main(String[] args) {
        List<Integer> taskQueue = new ArrayList<Integer>();
        int MAX_CAPCITY = 6;
        Thread producer = new Thread(new Producer(taskQueue, MAX_CAPCITY), "生产者");
        Thread cusumer = new Thread(new Consumer(taskQueue), "消费者");
        producer.start();
        cusumer.start();
    }
}
