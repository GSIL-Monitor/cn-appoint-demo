package com.jd.appoint.vo;

import java.io.Serializable;

/**
 * Created by yangyuan on 6/5/18.
 */
public class Pair<K, V> implements Serializable {
    private static final String DEFAULT_SPLITER = ":";

    private K key;
    private V value;

    public Pair() {
    }

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public static Pair<Integer, String> toPair(String str) {
        String[] array = str.split(DEFAULT_SPLITER);
        return new Pair(Integer.valueOf(array[0].trim()), array[1]);
    }

    public static Pair<String, String> toStrPair(String str) {
        String[] array = str.split(DEFAULT_SPLITER);
        return new Pair(array[0].trim(), array[1]);
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
