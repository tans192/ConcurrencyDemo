package chapter_1.notify.libraryexample;

/**
 * @author xiang.wei
 * @date 2019/11/1 18:31
 */
public class LibraryTest1 {
    private boolean canBorrow = false;

    synchronized boolean getCanBorrow() {
        System.out.println("******获取到的="+canBorrow);
        return canBorrow;
    }

    synchronized void setCanBorrow(boolean canBorrow) {
        this.canBorrow = canBorrow;
    }

    public static void main(String[] args) throws InterruptedException {
       final LibraryTest1 libraryTest1 = new LibraryTest1();
        Thread threadA = new Thread(new Runnable() {
            public void run() {
                libraryTest1.setCanBorrow(true);
            }
        }, "小A");
        Thread threadB = new Thread(new Runnable() {
            public void run() {
                libraryTest1.getCanBorrow();
            }
        }, "小B");
        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();

    }
}
