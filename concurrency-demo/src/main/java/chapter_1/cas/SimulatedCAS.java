package chapter_1.cas;

/**
 * @author xiang.wei
 * @date 2019/11/16 13:32
 */
public class SimulatedCAS {
    int count;

    synchronized int cas(int expectValue, int newValue) {
        //读目前count的值
        int curValue = count;
        //比较目前count值是否==期望值
        if (curValue == expectValue) {
            //如果是，则更新count的值
            count = newValue;
        }
        //返回写入前的值
        return curValue;
    }
}
