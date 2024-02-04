package chapter_1.notify;

/**
 * 不要在字符串常量或全局对象中调用wait()
 * @author xiang.wei
 * @date 2019/11/1 15:39
 */
public class MyWaitNotify3 {

    String monitorObject = "";
    boolean isSignaled = false;

    public void doWait() {
        synchronized (monitorObject) {
            while (!isSignaled) {
                try {
                    monitorObject.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //清楚标记，继续执行
            isSignaled = false;
        }
    }

    public void doNotify() {
        synchronized (monitorObject) {
            isSignaled = true;
            monitorObject.notifyAll();
        }
    }

}
