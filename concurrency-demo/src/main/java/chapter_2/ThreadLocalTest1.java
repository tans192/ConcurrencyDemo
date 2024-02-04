package chapter_2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xiang.wei on 2019/11/9
 *
 * @author xiang.wei
 */
public class ThreadLocalTest1 {
    private static final ThreadLocal<User> userLocal = new ThreadLocal<User>();



    static class SetUser implements Runnable{
        private User user;

        SetUser(User user) {
            this.user = user;
        }

        public void run() {
            System.out.println("*******线程="+Thread.currentThread().getName()+"设置的user"+user);
            userLocal.set(user);
        }
    }


    public User getUser() {
        return userLocal.get();
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newScheduledThreadPool(10);
        final ThreadLocalTest1 threadLocalTest1 = new ThreadLocalTest1();
        for (int i = 1; i < 10; i++) {
            User user = new User();
            user.setId(i);
            user.setName("测试"+i);
            executorService.submit(new SetUser(user));
            executorService.submit(new Runnable() {
                public void run() {
                    User user = threadLocalTest1.getUser();
                    System.out.println("********线程="+Thread.currentThread().getName()+"获取到的user"+user);
                }
            });
        }
        executorService.shutdown();
    }
}
