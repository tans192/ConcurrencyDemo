package chapter_1.performance;


import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 通过一个代理类，封装Map接口，主要是对其进行安全性处理
 * Created by xiang.wei on 2019/11/17
 *
 * @author xiang.wei
 */
public class LockMapProxy<K, V> implements Map<K, V> {

    private Map<K, V> origin;

    private ReentrantLock lock;

    public LockMapProxy(Map<K, V> origin) {
        this.origin = origin;
        this.lock = new ReentrantLock();
    }

    public <K, V> LockMapProxy<K, V> LockMap(Map<K, V> map) {
        return new LockMapProxy<K, V>(map);
    }

    public int size() {
        return 0;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean containsKey(Object key) {
        return false;
    }

    public boolean containsValue(Object value) {
        return false;
    }

    public V get(Object key) {
        return origin.get(key);
    }

    public V put(K key, V value) {
        lock.lock();
        try {
            return origin.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    public V remove(Object key) {
        return null;
    }

    public void putAll(Map<? extends K, ? extends V> m) {

    }

    public void clear() {
        lock.lock();
        try {
            origin.clear();
        }finally {
            lock.unlock();
        }
    }

    public Set<K> keySet() {
        return null;
    }

    public Collection<V> values() {
        return null;
    }

    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
