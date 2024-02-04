package chapter_3.test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiang.wei
 * @date 2019/12/23 16:39
 */
public class ExtentedHashMapTest {
    public static void main(String[] args) {
        Map<Integer, String> extentedHashMap = new HashMap<>(13, 1);
        extentedHashMap.put(1, "测试3");
        extentedHashMap.put(2, "测试7");
        extentedHashMap.put(3, "测试5");
        extentedHashMap.put(4, "测试3");
        for (Integer key : extentedHashMap.keySet()) {
            System.out.println("key=" + key + "value=" + extentedHashMap.get(key));
        }
    }
}
