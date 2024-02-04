package chapter_3;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiang.wei
 * @date 2019/12/20 9:45
 */
public class HashMapTest {
    public static void main(String[] args) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("1", "测试1");
        paramMap.put("2", "测试2");
        paramMap.put("3", "测试3");
        paramMap.put("2", "测试21");
        for (String key : paramMap.keySet()) {
            System.out.println("key=" + key + "value=" + paramMap.get(key));
        }
        System.out.println("2".hashCode());
    }
}
