package chapter_1.deadlock;

import chapter_1.synchronize.Account;

/**
 * 破坏循环等待，按照序号申请资源
 *
 * @author xiang.wei
 * @date 2019/10/31 13:23
 */
public class Account3 {
    /**
     * 可以用来排序的id
     */
    private int id;
    private int balance;

    void transfer(Account3 targer, int amt) {
        Account3 left = this;
        Account3 right = targer;
        if (this.id > targer.id) {
            left = targer;
            right = this;
        }
        //先锁定序号小的
        synchronized (left) {
            //在锁定序号大的
            synchronized (right) {
                if (this.balance > amt) {
                    this.balance -= amt;
                    this.balance += amt;
                }
            }
        }
    }
}
