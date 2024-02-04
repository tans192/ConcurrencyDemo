package chapter_1.notify;

import java.util.ArrayList;
import java.util.List;

/**
 * 用来申请资源的角色
 *
 * @author xiang.wei
 * @date 2019/10/31 11:17
 */
public class AllocatorNotify {
    private List<Object> applyList = new ArrayList<Object>();

    private final static AllocatorNotify allocator = new AllocatorNotify();

    private AllocatorNotify() {

    }

    /**
     * 只能由一个人完成，所以是单例模式
     *
     * @return
     */
    public static AllocatorNotify getAllocator() {
        return allocator;
    }

    /**
     * 申请资源
     */
    public synchronized void applyResource(Object from, Object to)  {
        if (applyList.contains(from) ||
                applyList.contains(to)) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        applyList.add(from);
        applyList.add(to);
    }

    /**
     * 释放资源
     */
    public synchronized void free(Object from, Object to) {
        applyList.remove(from);
        applyList.remove(to);
        notifyAll();
    }

}
