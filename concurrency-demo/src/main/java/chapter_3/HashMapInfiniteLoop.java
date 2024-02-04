package chapter_3;

import java.util.HashMap;

/**
 * @author xiang.wei
 * @date 2019/12/20 15:54
 */
public class HashMapInfiniteLoop {
    private static HashMap<String, String> map = new HashMap<>(2, 0.75f);

    public static void main(String[] args) {
        map.put("5", "C");

    }
}
