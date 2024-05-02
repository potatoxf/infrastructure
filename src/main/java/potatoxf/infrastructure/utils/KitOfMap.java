package potatoxf.infrastructure.utils;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntFunction;

/**
 * Map工具类
 * <p/>
 * Create Time:2024-04-20
 *
 * @author potatoxf
 */
public class KitOfMap {

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
    public static <OK, OV, NK, NV> Map<NK, NV> copy(Map<OK, OV> input, Function<OK, NK> keyHandler, Function<OV, NV> valueHandler,
                                                    IntFunction<Map<NK, NV>> creator, boolean nullKey, boolean nullValue, boolean unmodifiable) {
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
}
