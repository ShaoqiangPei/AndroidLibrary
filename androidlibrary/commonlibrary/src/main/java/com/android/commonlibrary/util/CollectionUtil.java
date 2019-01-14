package com.android.commonlibrary.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author pei
 */
public class CollectionUtil {

    public static <T> List<T> paging(List<T> list, int page, int count) {
        List<T> temp = null;
        if(isNotEmpty(list)) {
            int size = list.size();
            int begin = count * (page - 1);
            int end = count * page;
            if (end <= size) {
                temp = list.subList(begin, end);
            } else if (begin < size && end > size) {
                temp = list.subList(begin, size);
            } else {
                temp = null;
            }
        }
        return temp;
    }

    /**
     * merge source to dest
     *
     * @param dest
     * @param source
     * @return dest
     */
    public static <T> List<T> merge(List<T> dest, List<T> source) {
        if (null == dest) {
            dest = new ArrayList<T>();
        }
        if (isNotEmpty(source)) {
            dest.addAll(source);
        }
        return dest;
    }

    /**
     * converter Set to List
     * @param set
     * @param <T>
     * @return
     */
    public static <T> List<T> converter(Set<T> set) {
        List<T> t = null;
        if (isNotEmpty(set)) {
            t = new ArrayList<T>();
            t.addAll(set);
        }
        return t;
    }

    /**
     * check the set is null or is empty
     *
     * @param set
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(Set<T> set) {
        if (null == set || set.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * check the set is not empty
     *
     * @param set
     * @return true means is not null
     */
    public static <T> boolean isNotEmpty(Set<T> set) {
        return !isEmpty(set);
    }

    /**
     * check the list is null or is empty
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(List<T> list) {
        if (null == list || list.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * check the list is not empty
     *
     * @param list
     * @return true means is not null
     */
    public static <T> boolean isNotEmpty(List<T> list) {
        return !isEmpty(list);
    }

    /**
     * check the list is null or is empty
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> boolean isEmpty(Map<K, V> map) {
        if (null == map || map.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * check the map is null or is empty
     *
     * @param map
     * @return true means is not null
     */
    public static <K, V> boolean isNotEmpty(Map<K, V> map) {
        return !isEmpty(map);
    }
}
