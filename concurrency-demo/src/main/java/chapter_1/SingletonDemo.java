package chapter_1;

/**
 * 双重加锁处理（会有有序性问题）
 *
 * @author xiang.wei
 * @date 2019/10/10 9:29
 */
public class SingletonDemo {
    private static volatile SingletonDemo instance = null;

    private SingletonDemo(){

    }

    static SingletonDemo getSingletonDemo() {
        if (instance == null) {
            synchronized (SingletonDemo.class) {
                if (instance == null) {
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }
}
