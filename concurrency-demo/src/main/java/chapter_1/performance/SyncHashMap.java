package chapter_1.performance;

import java.util.HashMap;

/**
 * Created by xiang.wei on 2019/11/17
 *
 * @author xiang.wei
 */
public class SyncHashMap<K,V> extends HashMap<K,V> {

    @Override
    public V get(Object key) {
        synchronized (this) {
            return super.get(key);
        }
    }

    @Override
    public V put(K key, V value) {
        synchronized (this) {
            return super.put(key, value);
        }
    }

}
