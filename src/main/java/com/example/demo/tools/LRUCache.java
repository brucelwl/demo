package com.example.demo.tools;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * LinkedHashMap 不是线程安全的,为什么能做缓存?
 * <p>
 * Created by bruce on 2020/4/10 15:32
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private static final long serialVersionUID = -9169922117343161205L;

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    private int maxCacheSize;

    public LRUCache(int maxCacheSize) {
        super(maxCacheSize, 1F, true);
        this.maxCacheSize = maxCacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxCacheSize;
    }

    @Override
    public int size() {
        try {
            readLock.lock();
            return super.size();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        try {
            readLock.lock();
            return super.isEmpty();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsKey(Object key) {
        try {
            readLock.lock();
            return super.containsKey(key);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsValue(Object value) {
        try {
            readLock.lock();
            return super.containsValue(value);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public V get(Object key) {
        try {
            readLock.lock();
            return super.get(key);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public V put(K key, V value) {
        try {
            writeLock.lock();
            return super.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public V remove(Object key) {
        try {
            writeLock.lock();
            return super.remove(key);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        try {
            writeLock.lock();
            super.putAll(m);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void clear() {
        try {
            writeLock.lock();
            super.clear();
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        V v = get(key);
        return v != null ? v : defaultValue;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V putIfAbsent(K key, V value) {
        V v = get(key);
        if (v != null) {
            return v;
        }
        try {
            writeLock.lock();
            return super.putIfAbsent(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean remove(Object key, Object value) {
        try {
            writeLock.lock();
            return super.remove(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        try {
            writeLock.lock();
            return super.replace(key, oldValue, newValue);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public V replace(K key, V value) {
        try {
            writeLock.lock();
            return super.replace(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        V v = get(key);
        if (v != null) {
            return v;
        }
        try {
            writeLock.lock();
            return super.computeIfAbsent(key, mappingFunction);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        V v = get(key);
        if (v == null) {
            return null;
        }
        try {
            writeLock.lock();
            return super.computeIfPresent(key, remappingFunction);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        try {
            writeLock.lock();
            return super.merge(key, value, remappingFunction);
        } finally {
            writeLock.unlock();
        }
    }


}
