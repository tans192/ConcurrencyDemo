package chapter_1.notify;

/**
 * @author xiang.wei
 * @date 2019/11/1 15:39
 */
public class MyWaitNotify2 {

    MonitorObject monitorObject = new MonitorObject();
    boolean isSignaled = false;

    public void doWait() {
        synchronized (monitorObject) {
            if (!isSignaled) {
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
