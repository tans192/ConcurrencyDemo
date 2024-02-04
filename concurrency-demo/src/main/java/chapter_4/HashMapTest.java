package chapter_4;

import java.util.HashMap;

/**
 * Created by xiang.wei on 2020/1/31
 *
 * @author xiang.wei
 */
public class HashMapTest {
    public static void main(String[] args) {
        HashMap<Integer, Integer> paramMap = new HashMap<>();
        paramMap.put(31, 121);
        paramMap.put(14, 14);
        System.out.println("******map的长度=" + paramMap.size());
        for (Integer key : paramMap.keySet()) {
            System.out.println("*******遍历map="+paramMap.get(key));
        }
    }
}
