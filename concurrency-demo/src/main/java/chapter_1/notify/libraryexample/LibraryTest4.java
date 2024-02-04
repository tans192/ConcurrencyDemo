package chapter_1.notify.libraryexample;

import chapter_1.notify.MonitorObject;

/**
 * @author xiang.wei
 * @date 2019/11/1 19:34
 */
public class LibraryTest4 {
    private MonitorObject monitorObject = new MonitorObject();
    private boolean canBorrow = false;
    private boolean wasSignalled = false;

    String borrowBook() throws InterruptedException {
        synchronized (monitorObject) {
            while (!canBorrow||!wasSignalled) {
                wait();
                return null;
            }
            canBorrow = false;
            return "Java 高并发实战";
        }
    }

    void giveBackBook() {
        synchronized (monitorObject) {
            this.canBorrow = true;
            this.wasSignalled = true;
            notifyAll();
        }
    }
}
