package com.bootdo.clouddoadmin.utils;

/**
 * @Author : liushan
 * @Date : 2019/11/1 2:59 PM
 */
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BeanUtils {
    private static final Logger log = LoggerFactory.getLogger(BeanUtils.class);

    /**
     * 拷贝属性
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target){
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }

    /**
     * 拷贝属性
     * @param source 源对象
     * @param clazz 目标对象类型
     * @return 目标对象
     */
    public static <S,T> T copyProperties(S source, Class<T> clazz){
        if(source == null) { return null; }
        try {
            T target = clazz.newInstance();
            copyProperties(source, target);
            return target;
        } catch (InstantiationException e) {
            log.error("Copy Bean Error!",e);
        } catch (IllegalAccessException e) {
            log.error("Copy Bean Error!",e);
        }
        return null;
    }

    /**
     * 拷贝属性集合
     * @param sList 源对象列表
     * @param clazz 目标对象类型
     * @return 目标对象集合
     */
    public static <S,T> List<T> copyProperties(List<S> sList, Class<T> clazz){
        if(sList == null || sList.isEmpty()) { return null; }
        if(clazz == null) { throw new IllegalArgumentException("Target Class Can Not Be Null!"); }

        List<T> retList = Lists.newArrayList();
        for (S s : sList) {
            retList.add(copyProperties(s, clazz));
        }
        return retList;
    }

    /**
     * 属性映射
     * @param source 源对象
     * @param clazz 属性转换器类型
     * @return 目标对象
     */
    public static <S, T> T map(S source, Class<? extends SafeFunction<? super S, ? extends T>> clazz) {
        if(source == null) { return null; }
        if(clazz == null) { throw new IllegalArgumentException("Transformer Can Not Be Null!"); }

        try {
            SafeFunction<? super S, ? extends T> function = clazz.newInstance();
            return function.apply(source);
        } catch (InstantiationException e) {
            log.error("Mapping Bean Error!",e);
        } catch (IllegalAccessException e) {
            log.error("Mapping Bean Error!",e);
        }
        return null;
    }

    public static <S, T> T map(S source, SafeFunction<? super S, ? extends T> function){
        if(source == null) { return null; }
        if(function == null) { throw new IllegalArgumentException("Transformer Can Not Be Null!"); }
        return function.apply(source);
    }

    /**
     * 集合属性映射
     * @param sList 源对象列表
     * @param clazz 属性转换器类型
     * @return 目标对象列表
     */
    public static <S, T> List<T> map(List<S> sList, Class<? extends SafeFunction<? super S, ? extends T>> clazz) {
        if(sList == null) { return null; }
        if(clazz == null) { throw new IllegalArgumentException("Transformer Can Not Be Null!"); }

        try {
            SafeFunction<? super S, ? extends T> function = clazz.newInstance();
            List<T> retList = Lists.newArrayList();
            for (S s : sList) {
                retList.add(function.apply(s));
            }
            return retList;
        } catch (InstantiationException e) {
            log.error("Mapping Bean Error!",e);
        } catch (IllegalAccessException e) {
            log.error("Mapping Bean Error!",e);
        }
        return null;
    }


    public static <K, V, R> Map<K, List<R>> listToHashMap(SafeFunction<V, K> func1, SafeFunction<V, List<R>> func2, List<V> list) {
        Map<K, List<R>> map = Maps.newHashMap();
        if(CollectionUtils.isEmpty(list)){
            return map;
        }
        for (V v : list) {
            map.put(func1.apply(v), func2.apply(v));
        }
        return map;
    }

    public static <K,V> HashMultimap<K, V> listToMultiMap(SafeFunction<V, K> function, List<V> list){
        HashMultimap<K, V> map = HashMultimap.create();
        if(CollectionUtils.isEmpty(list)){
            return map;
        }
        for (V v : list) {
            map.put(function.apply(v), v);
        }
        return map;
    }

    public static <T,R> List<R> distinctFieldsList(SafeFunction<T, R> function, List<T> list){
        if(CollectionUtils.isEmpty(list)){
            return Lists.newArrayList();
        }
        Set<R> set = Sets.newHashSet();
        for (T t : list) {
            set.add(function.apply(t));
        }
        return Lists.newArrayList(set);
    }

    public static <T,R> Set<R> buildFieldSet(List<T> list, SafeFunction<T, R> function){
        if(CollectionUtils.isEmpty(list)){
            return Sets.newHashSet();
        }
        Set<R> set = Sets.newHashSet();
        for (T t : list) {
            set.add(function.apply(t));
        }
        return set;
    }

}

