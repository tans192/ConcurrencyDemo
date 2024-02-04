package chapter_1.reentrantLock.producerLock;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiang.wei
 * @date 2019/11/15 9:51
 */
public class LockConditionTest {

    public static void main(String[] args) {
        final Lock lock = new ReentrantLock();
        Condition notFull = lock.newCondition();
        Condition notEmpty = lock.newCondition();

        List<Integer> taskQueue = new ArrayList<Integer>();
        int MAX_CAPCITY = 6;
        Thread producer = new Thread(new Producer(taskQueue, MAX_CAPCITY, lock, notFull, notEmpty), "生产者");
        Thread cusumer = new Thread(new Consumer(taskQueue, lock, notFull, notEmpty), "消费者");
        producer.start();
        cusumer.start();
    }
}
