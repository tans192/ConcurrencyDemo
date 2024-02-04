package chapter_4.queue;

/**
 * 用数组实现队列
 * Created by xiang.wei on 2020/2/2
 *
 * @author xiang.wei
 */
public class ArrayQueue {
    /**
     * 数组：items,数组大小：n
     */
    private String[] items;
    private int n = 0;

    /**
     * head表示队头下标
     */
    private int head = 0;
    /**
     * tail表示队尾下标
     */
    private int tail = 0;

    /**
     * 申请一个容量为capacity的队列
     * @param capacity 队列的容量
     */
    public ArrayQueue(int capacity) {
        items = new String[capacity];
        n = capacity;
    }

    /**
     * //入队
     * @param item
     * @return
     */
    public boolean enqueue(String item) {
        //如果tail==n 表示队列已经满了
        if (tail == n) {
            return false;
        }
        items[tail] = item;
        ++tail;
        return true;
    }

    public boolean enqueueNew(String item) {
        //tail==n 表示队列末尾没有空间了
        if (tail == n) {
            //tail==n && head==0 表示整个队列都占满了
            if (head == 0) {
                return false;
            }
            //数据搬移
            for (int i=head;i<tail;++i) {
                items[i - head] = items[i];
            }
            //搬移完之后重新更新head和tail
            tail -= head;
            head = 0;
        }
        items[tail] = item;
        ++tail;
        return true;
    }
    /**
     * 出队
     * @return
     */
    public String dequeue() {
        //如果head==tail,表示队列为空
        if (head == tail) {
            return null;
        }
        String ret = items[head];
        ++head;
        return ret;
    }

    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue(15);
        for (int i = 0; i < 15; i++) {
            arrayQueue.enqueue("测试" + i);
            System.out.println("********入队的数据是={}"+"测试" + i);
        }
        for (int j=0;j<15;j++) {
            arrayQueue.dequeue();
            System.out.println("********出队的数据是={}"+"测试" + j);
        }
    }
}
