package chapter_1.synchronize;

import java.util.Random;

/**
 * 存款取款和修改密码
 *
 * @author xiang.wei
 * @date 2019/10/10 19:21
 */
public class Account {
    //密码保护锁
    private final Object pwdLock = new Object();

    public Integer balance = 1000;

    public String password = null;

    //取款保护锁
    private final Object balLock = new Object();
    /**
     * 取款
     */
    public void withdrow(Integer amt) throws InterruptedException {
        synchronized (balLock) {
            if (balance > amt) {
                balance -= amt;
                Thread.sleep(100);
                System.out.println("*******" + Thread.currentThread().getName() + "扣除后的余额是=" + balance);
            }
        }
    }
    /**
     * 查看余额
     *
     * @return
     */
    public int getSBalance() throws InterruptedException {
        synchronized (balLock) {
            Thread.sleep(100);
            System.out.println("*******" + Thread.currentThread().getName() + "读取到的余额是=" + balance);
            return balance;
        }
    }
    /**
     * 更改密码
     *
     * @param newPwd
     */
    public void updatePwd(String newPwd) {
        synchronized (pwdLock) {
            System.out.println("*******" + Thread.currentThread().getName() + "修改密码是=" + newPwd);
            password = newPwd;
        }
    }

    /**
     * 查看密码
     *
     * @return
     */
    public String getNewPwd() {
        synchronized (pwdLock) {
            System.out.println("*******" + Thread.currentThread().getName() + "读取到密码是=" + password);
            return password;
        }
    }

    public static void main(String[] args) {
        final Account account = new Account();
        for (int i = 0; i < 5; i++) {
            Thread threadA = new Thread(new Runnable() {
                public void run() {
                    try {
                        account.withdrow(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    account.updatePwd(String.valueOf( new Random().nextInt(9999)));
                }
            }, "写线程" + i);
            Thread threadB = new Thread(new Runnable() {
                public void run() {
                    try {
                        account.getSBalance();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    account.getNewPwd();
                }
            }, "读线程" + i);
            threadA.start();
            threadB.start();
        }
    }
}
