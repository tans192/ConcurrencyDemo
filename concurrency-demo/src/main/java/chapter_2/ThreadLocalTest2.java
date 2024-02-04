package chapter_2;

/**
 * Created by xiang.wei on 2019/11/9
 *
 * @author xiang.wei
 */
public class ThreadLocalTest2 {
    private static final ThreadLocal<User> userLocal = new ThreadLocal<User>();


    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                System.out.println("*******线程"+Thread.currentThread().getName()+"的值是"+userLocal.get());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                User user = new User();
                user.setId(1);
                user.setName("测试1");
                userLocal.set(user);
                System.out.println("*******线程"+Thread.currentThread().getName()+"的值是"+userLocal.get());
            }

        },"线程1").start();
        new Thread(new Runnable() {
            public void run() {
                User user = new User();
                user.setId(2);
                user.setName("测试2");
                userLocal.set(user);
                System.out.println("*******线程"+Thread.currentThread().getName()+"的值是"+userLocal.get());
            }
        },"线程2").start();
    }
}

