package chapter_1;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author xiang.wei
 * @date 2019/10/15 19:17
 */
public class SafeWM {
    /**
     * 上限
     */
    private volatile   AtomicLong upper = new AtomicLong(0);
    /**
     * 下限
     */
    private final AtomicLong low = new AtomicLong(0);



    public synchronized void setUpper(long v) {
        if (v < low.longValue()) {
            throw new IllegalArgumentException("非法的上限");
        }
        upper.set(v);
    }

    public synchronized void setLow(long v) {
        if (v > upper.longValue()) {
            throw new IllegalArgumentException("非法的下限");
        }
        low.set(v);
    }
}
