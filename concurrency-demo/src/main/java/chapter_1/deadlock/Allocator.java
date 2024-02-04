package chapter_1.deadlock;

import java.util.ArrayList;
import java.util.List;

/**
 * 用来申请资源的角色
 *
 * @author xiang.wei
 * @date 2019/10/31 11:17
 */
public class Allocator {
    private List<Object> applyList = new ArrayList<Object>();

    private final static Allocator allocator = new Allocator();

    private Allocator() {

    }

    /**
     * 只能由一个人完成，所以是单例模式
     *
     * @return
     */
    public static Allocator getAllocator() {
        return allocator;
    }

    /**
     * 申请资源
     */
    public synchronized boolean applyResource(Object from, Object to) {
        if (applyList.contains(from) ||
                applyList.contains(to)) {
            return false;
        }
        applyList.add(from);
        applyList.add(to);
        return true;
    }

    /**
     * 释放资源
     */
    public synchronized void free(Object from, Object to) {
        applyList.remove(from);
        applyList.remove(to);
    }

}
