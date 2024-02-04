package chapter_4.queue_10;

/**
 * Created by xiang.wei on 2020/2/5
 *
 * @author xiang.wei
 */
public class CircularQueue {
    /**
     * 定义数组
     */
    private String[] items;
    /**
     * 头指针
     */
    private int head = 0;
    /**
     * 尾指针
     */
    private int tail = 0;
    /**
     * 数组大小
     */
    private int n = 0;

    public CircularQueue(int capacity) {
        items = new String[capacity];
        n = capacity;
    }

    /**
     * 入队
     *
     * @param item
     * @return
     */
    public boolean enqueue(String item) {
        //队列已满
        if ((tail + 1) % n == head) {
            return false;
        }
        items[tail] = item;
        tail = (tail + 1) % n;
        return true;
    }

    /**
     * 出队
     *
     * @return
     */
    public String dequeue() {
        //队列为空
        if (head == tail) {
            return null;
        }
        String item = items[head];
        head = (head + 1) % n;
        return item;
    }

    /**
     * 打印队列
     *
     * @return
     */
    public String printAll() {
        StringBuilder builder = new StringBuilder();
        for (int i = head; i < n; i++) {
            builder.append("元素：" + items[i]);
        }
        return builder.toString();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        CircularQueue circularQueue = new CircularQueue(7);
        for (int i = 0; i < circularQueue.n; i++) {
            circularQueue.enqueue("测试" + i);
        }
        System.out.println("队列里的数据是={}" + circularQueue.printAll());
        for (int i = 0; i < circularQueue.n; i++) {
            System.out.println("当前出队的元素是=" + circularQueue.dequeue());
        }
    }
}
