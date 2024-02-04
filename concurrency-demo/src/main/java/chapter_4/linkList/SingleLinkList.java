package chapter_4.linkList;

/**
 * Created by xiang.wei on 2020/1/31
 *
 * @author xiang.wei
 */
public class SingleLinkList {
    /**
     * 头结点¡
     */
    private Node head;
    /**
     * 当前节点
     */
    private Node current;
    /**
     * 大小
     */
    private int size;

    public SingleLinkList() {
        //默认头结点为null
        head = current = new Node(null);
        size = 0;
    }

    /**
     * 尾插法
     */
    public void addLast(Object value) {
        Node newNode = new Node(value);
        while (current.next != null) {
            //获取尾结点
            current = current.next;
        }
        current.next = newNode;
        newNode.next = null;
        ++size;
    }

    /**
     * 头插法
     *
     * @param value
     */
    public void addHead(Object value) {
        Node newNode = new Node(value);
        int j = 0;
        //后一个结点
        current = head;
        if (current.next != null && j == 0) {
            //获取尾结点
            current = current.next;
        }
        newNode.next = current;
        head.next = newNode;
        ++size;
    }

    /**
     * 得到节点的值
     *
     * @param i
     */
    public Object getValue(int i) {
        current = head.next;
        int j = 0;
        while (current.next != null && j < i) {
            current = current.next;
            j++;
        }
        return current.value;
    }

    /**
     * 在指定位置插入元素
     * @param i
     * @param value
     */
    public void insert(int i, Object value) {
        Node newNode = new Node(value);
        //前一个结点
        Node pre = head;
        //后一个结点
        current = head.next;
        int j = 0;
        while (current != null && j < i) {
            pre = current;
            current = current.next;
            j++;
        }
        pre.next = newNode;
        newNode.next = current;
        ++size;
    }

    /**
     * 删除指定位置上的结点
     *
     * @param i
     */
    public void delete(int i) {
        Node pre = head;
        current = head.next;
        int j = 0;
        while (current != null && j < i) {
            pre = current;
            current = current.next;
            j++;
        }
        pre.next = current.next;
        size--;
    }

    /**
     * 遍历打印节点
     */
    public String printLinkList() {
        if (isEmpty()) {
            System.out.println("*********链表为空");
        }
        StringBuilder linkListSB = new StringBuilder();
        for (current = head.next; current != null; current = current.next) {
            linkListSB.append(current.value+"--->");
        }
        return linkListSB.toString();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
    class Node {
        /**
         * 数据域
         */
        Object value;
        /**
         * 下一个结点对象的引用
         */
        Node next;

        public Node(Object value, Node next) {
            this.value = value;
            this.next = next;
        }

        public Node(Object value) {
            this.value = value;
        }

        public Node(Node next) {
            this.next = next;
        }

    }

    public static void main(String[] args) {
        SingleLinkList singleLinkList = new SingleLinkList();

        //初始化一个长度为6的链表
        for (int i = 1; i < 7; i++) {
            singleLinkList.addLast("Node" + i);
        }
        System.out.println("初始化一个长度为6的链表之后\n"+singleLinkList.printLinkList());;

        //在Node3和Node4结点之间插入Node7
        singleLinkList.insert(2, "Node7");
        System.out.println("在Node3和Node4结点之间插入Node7之后\n"+singleLinkList.printLinkList());;

        //在链表头部插入元素Node8
        singleLinkList.addHead("Node8");
        System.out.println("在链表头部插入元素Node8之后\n"+singleLinkList.printLinkList());;

        //删除第Node3结点
        singleLinkList.delete(2);
        System.out.println("删除第Node3结点之后\n"+singleLinkList.printLinkList());;

        //获取第5为的节点
        String value = (String) singleLinkList.getValue(4);
        System.out.println("*******第五位的获取到的节点是"+value);

    }
}
