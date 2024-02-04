package chapter_1;

/**
 * 静态内部类处理
 *
 * @author xiang.wei
 * @date 2019/10/10 9:36
 */
public class MySingleton {
    private static class MySingletonHandle {
        private static final MySingleton instance = new MySingleton();
    }

    private MySingleton() {

    }

    public MySingleton getMySingleton() {
        return MySingletonHandle.instance;
    }
}
