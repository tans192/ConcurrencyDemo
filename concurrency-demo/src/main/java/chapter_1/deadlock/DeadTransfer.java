package chapter_1.deadlock;

import chapter_1.synchronize.Account;

/**
 * @author xiang.wei
 * @date 2019/10/31 10:44
 */
public class DeadTransfer {
    private Integer balance;

    void transfer2(Account target, int amt) {
        synchronized (this) {
            if (balance > amt) {
                this.balance -= amt;
                synchronized (target) {
                    target.balance = target.balance + amt;
                }
            }
        }
    }
}
