package chapter_4;

/**
 * @author xiang.wei
 * @date 2019/12/20 16:07
 */
public class NodeDemo {
    private NodeData nodeData;
    private NodeDemo nodeNext;

    /**
     * 在链尾添加一个结点
     *
     * @param head
     * @param nodeData
     * @return
     * @author xiang.wei
     * @date 2019/12/20  16:09
     */
    public NodeDemo addNode(NodeDemo head, NodeData nodeData) {
        if (head == null || nodeData == null) {
            return null;
        }
        //1.内存不足无法添加结点
        NodeDemo nodeDemo;
        if ((nodeDemo = new NodeDemo()) == null) {
            System.out.println("内存不足无法添加结点");
            return head;
        }
        //2.保存结点
        nodeDemo.nodeData = nodeData;
        nodeDemo.nodeNext = null;
        //添加结点
        if (head.nodeNext == null) {
            head.nodeNext = nodeDemo;
            return head;
        }
        while (head.nodeNext != null) {
            head = head.nodeNext;
        }
        head.nodeNext = nodeDemo;
        return nodeDemo;
    }

    /**
     * 用于删除一个指定的节点
     *
     * @param head 传入一个链表节点
     * @param no   传入一个序号，删除这个序号的节点
     * @return 返回一个单向链表
     */
    public NodeDemo delNode(NodeDemo head, int no)
    {
        if (head == null || no == 0) return null;

        NodeDemo tmp = null;
        while (head.nodeNext != null)
        {
            /**
             * tmp 存放上次遍历到的节点
             * head 存放当前遍历到的节点
             */
            tmp = head;
            head = head.nodeNext;

            /**
             * 如果找到要删除的节点
             * 上次变量到的节点 tmp 指向 head.nodeNext
             * 删除 head 节点
             */
            if (head.nodeData.getId() == no)
            {
                tmp.nodeNext = head.nodeNext;
                head = null;
                break;
            }
        }

        return tmp;
    }

    /**
     * 用于打印每个节点的数据
     *
     * @param head 传入一个单向链表
     */
    public void print(NodeDemo head)
    {
        if (head == null) return;

        if (head.nodeNext == null)
            System.out.println("这是空链表");
        else
        {
            while (head.nodeNext != null)
            {
                head = head.nodeNext;
                System.out.println(head.nodeData.toString());
            }
        }
    }

    public static void main(String[] args)
    {
        NodeDemo demo = new NodeDemo();

        //1.向链表添加节点
        for (int i = 1; i <= 10; i++)
            demo.addNode(demo, new NodeData(i, "第" + i + "个节点"));

        demo.print(demo);
        System.out.println();

        //2.删除链表中的节点
        demo.delNode(demo, 5);
        demo.print(demo);
    }
}

