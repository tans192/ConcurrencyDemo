package chapter_4.queue;

/**
 * Created by xiang.wei on 2020/2/4
 *
 * @author xiang.wei
 */
public class LinkQueue {
    /**
     * 队列的队首
     */
    private volatile Node head;

    /**
     * 队列的队尾
     */
    private volatile Node tail;

    /**
     * 队列的长度
     */
    private  int size;

    /**
     * 初始化队列
     */
    public LinkQueue() {
        head = tail = null;
    }

    /**
     * 入队
     *
     * @param item
     */
    public void enqueue(String item) {
        Node newNode = new Node(item);
        //队列没有元素
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = tail.next;
        }
    }

    /**
     * 出队
     *
     * @return
     */
    public Object dequeue() {
        //队列为空
        if (head == null) {
            return null;
        }
        String value = head.value;
        head = head.next;
        //队列为空
        if (head == null) {
            tail = null;
        }
        return value;
    }

    /**
     * 遍历队列
     * @return
     */
    public  String printQueue() {
        StringBuilder queueStr = new StringBuilder();
        Node p = head;
        while (p != null) {
            queueStr.append(p.value+"-->");
            p = p.next;

        }
        return queueStr.toString();
    }
    public static void main(String[] args) {
        LinkQueue linkQueue = new LinkQueue();
        for (int i = 0; i < 6; i++) {
            linkQueue.enqueue("测试" + i);
        }
        System.out.println("队列="+linkQueue.printQueue());
        for (int i = 0; i < linkQueue.size; i++) {
            linkQueue.dequeue();
        }
        System.out.println("队列="+linkQueue.printQueue());

    }


    class Node {
        /**
         * 数据域
         */
        String value;
        /**
         * 下一个结点对象的引用
         */
        Node next;

        public Node(String value, Node next) {
            this.value = value;
            this.next = next;
        }

        public Node(String value) {
            this.value = value;
        }

        public Node(Node next) {
            this.next = next;
        }

    }
}
