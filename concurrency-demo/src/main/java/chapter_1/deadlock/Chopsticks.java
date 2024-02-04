package chapter_1.deadlock;

/**
 * @author xiang.wei
 * @date 2019/10/31 19:59
 */
public class Chopsticks {

    public void eat(Account2 target) {
        //没有申请到锁就一直循环下去,直到成功
        while (!Allocator.getAllocator().applyResource(this, target)) {
            return;
        }
        try {
            //左边
            synchronized (this) {
                //右边
                synchronized (target) {

                }
            }
        } finally {
            //释放已经申请的资源
            Allocator.getAllocator().free(this, target);
        }
    }

}
