package chapter_1.synchronize;

/**
 * 用balance做锁对象不可以，因为解锁时balance就已经发生了变化，
 * 锁的本质是在对象的头上加上线程id，所以加锁就无效了
 * @author xiang.wei
 * @date 2019/10/11 11:05
 */
public class BadAccount {
    private Integer balance = 1000;

    //取款保护锁
    private final Object balLock1 = new Object();

    //取款保护锁
    private final Object balLock2 = new Object();

    /**
     * 取款
     */
    public void withdrow(Integer amt) throws InterruptedException {
        synchronized (balLock1) {
            if (balance > amt) {
                balance -= amt;
                Thread.sleep(100);
                System.out.println("*******" + Thread.currentThread().getName() + "扣除后的余额是=" + balance);
            }
        }
    }

    /**
     * 查看余额
     * @return
     */
    public int getSBalance() throws InterruptedException {
        synchronized (balLock2) {
            Thread.sleep(100);
            System.out.println("*******" + Thread.currentThread().getName() + "读取到的余额是=" + balance);
            return balance;
        }
    }
    public static void main(String[] args) {
        final BadAccount account = new BadAccount();
        for (int i = 0; i < 5; i++) {
            Thread threadA = new Thread(new Runnable() {
                public void run() {
                    try {
                        account.withdrow(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "写线程" + i);
            Thread threadB = new Thread(new Runnable() {
                public void run() {
                    try {
                        account.getSBalance();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "读线程" + i);
            threadA.start();
            threadB.start();
        }
    }
}
