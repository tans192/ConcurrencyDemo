package chapter_1.deadlock;

import chapter_1.synchronize.Account;

/**
 * @author xiang.wei
 * @date 2019/10/10 19:21
 */
public class Account2 {

    private Integer balance;

    public void transfer(Account2 target, int amt) {
        //没有申请到锁就一直循环下去,直到成功
        while (!Allocator.getAllocator().applyResource(this, target)) {
            return;
        }
        try {
            //锁定转出账户
            synchronized (this) {
                //锁定转入账户
                synchronized (target) {
                    if (this.balance > amt) {
                        this.balance -= amt;
                        target.balance += amt;
                    }
                }
            }
        } finally {
            //释放已经申请的资源
            Allocator.getAllocator().free(this, target);
        }
    }

}
