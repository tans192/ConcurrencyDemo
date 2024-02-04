package chapter_1.performance;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * @author xiang.wei
 * @date 2019/11/16 17:29
 */
public class MapTest {


    public static void main(String[] args) {
        for (int i=0;i<5;i++){
            int rw_count = 10000 * (i + 1);
            int threadNum = 10* (11*i*i + 1);
            CyclicBarrier cb = new CyclicBarrier(threadNum * 2 + 1);
            System.out.println("=================================");
            System.out.println("线程数：" + threadNum + " ;每个线程操作的map次数：" + rw_count);
            new MapRunnable(new ConcurrentHashMap<Integer, String>(), threadNum, rw_count, cb).test("ConcurrentHashMap");
            new MapRunnable(new SyncHashMap<Integer, String>(), threadNum, rw_count, cb).test("synchronized");
            Map<Integer, String> map = new HashMap<Integer, String>();
            new MapRunnable(new LockMapProxy<Integer, String>(map), threadNum, rw_count, cb).test("lock");
        }
    }

}
