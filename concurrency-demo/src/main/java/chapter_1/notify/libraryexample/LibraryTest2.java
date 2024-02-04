package chapter_1.notify.libraryexample;

/**
 * @author xiang.wei
 * @date 2019/11/1 18:40
 */
public class LibraryTest2 {
    private boolean canBorrow = false;

    synchronized String borrowBook() throws InterruptedException {
        if (!canBorrow) {
            wait();
            return null;
        }
        return "Java 高并发实战";
    }

    synchronized void giveBackBook() {
        this.canBorrow = true;
        notifyAll();
    }




}
