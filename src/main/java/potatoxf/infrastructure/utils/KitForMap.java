package potatoxf.infrastructure.utils;

import potatoxf.infrastructure.value.multi.AnyTypePairVal;

import java.util.*;
import java.util.function.*;

/**
 * Map工具类
 * <p/>
 * Create Time:2024-04-20
 *
 * @author potatoxf
 */
public class KitForMap {

    /**
     * Map复制一份
     *
     * @param input        原来Map
     * @param keyHandler   key处理器
     * @param valueHandler value处理器
     * @param creator      新的Map创建器
     * @param nullKey      是否允许null为key
     * @param nullValue    是否允许null为value
     * @param unmodifiable 是否不运行修改
     * @param <OK>         原来key类型
     * @param <OV>         原来value类型
     * @param <NK>         新的key类型
     * @param <NV>         新的value类型
     * @return 返回 {@link Map}
     */
    public static <OK, OV, NK, NV> Map<NK, NV> copyToMap(Map<OK, OV> input, boolean nullKey, boolean nullValue, boolean unmodifiable, Function<OK, NK> keyHandler, Function<OV, NV> valueHandler, IntFunction<Map<NK, NV>> creator) {
        Map<NK, NV> result = creator.apply(input.size());
        for (Map.Entry<OK, OV> entry : input.entrySet()) {
            OK ok = entry.getKey();
            OV ov = entry.getValue();
            NK nk = null;
            NV nv = null;
            if (nullKey || ok != null) {
                nk = keyHandler.apply(ok);
            }
            if (nullValue || ov != null) {
                nv = valueHandler.apply(ov);
            }
            if (!nullKey && nk == null) continue;
            if (!nullValue && nv == null) continue;
            result.put(nk, nv);
        }
        return unmodifiable ? Collections.unmodifiableMap(result) : result;
    }

    /**
     * 将{@code Map<K, V>}处理成{@code Collection<E>}
     *
     * @param input             输入{@code Map<K, V>}
     * @param nullKey           是否允许key为null
     * @param nullValue         是否允许value为null
     * @param unmodifiable      是否不允许修改
     * @param handler           key与value处理对应元素
     * @param collectionCreator {@link Collection}创建器
     * @param <K>               key类型
     * @param <V>               value类型
     * @param <E>               处理后的元素类型
     * @return 返回{@code Collection<E>}
     */
    public static <K, V, E> Collection<E> copyToCollection(Map<K, V> input, boolean nullKey, boolean nullValue, boolean unmodifiable, BiFunction<K, V, E> handler, IntFunction<Collection<E>> collectionCreator) {
        Collection<E> result = collectionCreator.apply(input.size());
        for (Map.Entry<K, V> entry : input.entrySet()) {
            K k = entry.getKey();
            V v = entry.getValue();
            if (!nullKey && k == null) continue;
            if (!nullValue && v == null) continue;
            E e = handler.apply(k, v);
            if (e != null) result.add(e);
        }
        return unmodifiable ? Collections.unmodifiableCollection(result) : result;
    }

    /**
     * 将{@code Map<K, V>}处理成{@code List<E>}
     *
     * @param input        输入{@code Map<K, V>}
     * @param nullKey      是否允许key为null
     * @param nullValue    是否允许value为null
     * @param unmodifiable 是否不允许修改
     * @param handler      key与value处理对应元素
     * @param listCreator  {@link List}创建器
     * @param <K>          key类型
     * @param <V>          value类型
     * @param <E>          处理后的元素类型
     * @return 返回{@code List<E>}
     */
    public static <K, V, E> List<E> copyToList(Map<K, V> input, boolean nullKey, boolean nullValue, boolean unmodifiable, BiFunction<K, V, E> handler, IntFunction<List<E>> listCreator) {
        List<E> result = listCreator.apply(input.size());
        for (Map.Entry<K, V> entry : input.entrySet()) {
            K k = entry.getKey();
            V v = entry.getValue();
            if (!nullKey && k == null) continue;
            if (!nullValue && v == null) continue;
            E e = handler.apply(k, v);
            if (e != null) result.add(e);
        }
        return unmodifiable ? Collections.unmodifiableList(result) : result;
    }

    /**
     * 将{@code Map<K, V>}处理成{@code Set<E>}
     *
     * @param input        输入{@code Map<K, V>}
     * @param nullKey      是否允许key为null
     * @param nullValue    是否允许value为null
     * @param unmodifiable 是否不允许修改
     * @param handler      key与value处理对应元素
     * @param setCreator   {@link Set}创建器
     * @param <K>          key类型
     * @param <V>          value类型
     * @param <E>          处理后的元素类型
     * @return 返回{@code Set<E>}
     */
    public static <K, V, E> Set<E> copyToSet(Map<K, V> input, boolean nullKey, boolean nullValue, boolean unmodifiable, BiFunction<K, V, E> handler, IntFunction<Set<E>> setCreator) {
        Set<E> result = setCreator.apply(input.size());
        for (Map.Entry<K, V> entry : input.entrySet()) {
            K k = entry.getKey();
            V v = entry.getValue();
            if (!nullKey && k == null) continue;
            if (!nullValue && v == null) continue;
            E e = handler.apply(k, v);
            if (e != null) result.add(e);
        }
        return unmodifiable ? Collections.unmodifiableSet(result) : result;
    }

    /**
     * 复制表中行信息到{@code List<Map<K, V>>}
     *
     * @param t                        表结构形式数据
     * @param nullKey                  是否允许key为null
     * @param nullValue                是否允许value为null
     * @param unmodifiable             是否不允许修改
     * @param mapCreator               {@link Map}创建器
     * @param listCreator              {@link List}创建器
     * @param rowInformationInitialize 行信息初始化，初始化每一个行的信息
     * @param rowInformationGetters    行信息获取器，从单个行获取多个信息
     * @param <K>                      key类型
     * @param <V>                      value类型
     * @param <R>                      行类型
     * @param <T>                      表类型
     * @return 返回{@code List<Map<K, V>>}
     */
    @SafeVarargs
    public static <K, V, R, T extends Iterable<R>> List<Map<K, V>> copyToMapList(T t, boolean nullKey, boolean nullValue, boolean unmodifiable, Supplier<Map<K, V>> mapCreator, Supplier<List<Map<K, V>>> listCreator, Consumer<Map<K, V>> rowInformationInitialize, BiFunction<R, T, AnyTypePairVal<K, V>>... rowInformationGetters) {
        List<Map<K, V>> list = listCreator.get();
        for (R r : t) {
            Map<K, V> map = mapCreator.get();
            if (rowInformationInitialize != null) {
                rowInformationInitialize.accept(map);
            }
            for (BiFunction<R, T, AnyTypePairVal<K, V>> getter : rowInformationGetters) {
                AnyTypePairVal<K, V> val = getter.apply(r, t);
                if (val == null) continue;
                K k = val.getKey();
                V v = val.getValue();
                if (!nullKey && k == null) continue;
                if (!nullValue && v == null) continue;
                map.put(k, v);
            }
            list.add(map);
        }
        return list;
    }

    /**
     * 复制表中列信息到{@code List<Map<K, V>>}
     *
     * @param t                           表结构形式数据
     * @param nullKey                     是否允许key为null
     * @param nullValue                   是否允许value为null
     * @param unmodifiable                是否不允许修改
     * @param mapCreator                  {@link Map}创建器
     * @param listCreator                 {@link List}创建器
     * @param columnInformationInitialize 列信息初始化，初始化每一个列的信息
     * @param columnInformationGetters    列信息获取器，从单个列获取多个信息
     * @param <K>                         key类型
     * @param <V>                         value类型
     * @param <C>                         列类型
     * @param <R>                         行类型
     * @return 返回{@code List<Map<K, V>>}
     */
    @SafeVarargs
    public static <K, V, C, R extends Iterable<C>, T extends Iterable<R>> List<Map<K, V>> copyToMapList(T t, boolean nullKey, boolean nullValue, boolean unmodifiable, Supplier<Map<K, V>> mapCreator, Supplier<List<Map<K, V>>> listCreator, BiConsumer<Map<K, V>, R> columnInformationInitialize, BiFunction<C, R, AnyTypePairVal<K, V>>... columnInformationGetters) {
        List<Map<K, V>> list = listCreator.get();
        for (R r : t) {
            for (C c : r) {
                Map<K, V> map = mapCreator.get();
                if (columnInformationInitialize != null) {
                    columnInformationInitialize.accept(map, r);
                }
                for (BiFunction<C, R, AnyTypePairVal<K, V>> columnInformationGetter : columnInformationGetters) {
                    AnyTypePairVal<K, V> val = columnInformationGetter.apply(c, r);
                    if (val == null) continue;
                    K k = val.getKey();
                    V v = val.getValue();
                    if (!nullKey && k == null) continue;
                    if (!nullValue && v == null) continue;
                    map.put(k, v);
                }
                list.add(map);
            }
        }
        return list;
    }
}
