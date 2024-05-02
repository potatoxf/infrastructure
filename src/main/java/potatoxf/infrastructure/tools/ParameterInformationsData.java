package potatoxf.infrastructure.tools;

import potatoxf.infrastructure.Com;
import potatoxf.infrastructure.utils.KitForMap;
import potatoxf.infrastructure.value.multi.AnyTypePairVal;
import potatoxf.infrastructure.value.multi.AnyTypeTripleVal;

import java.util.*;
import java.util.concurrent.locks.StampedLock;

/**
 * 参数值信息数据，用于保存参数与参数值
 * <p/>
 * Create Time:2024-05-02
 *
 * @author potatoxf
 */
public class ParameterInformationsData extends ParameterInformations {
    /**
     * 参数表
     */
    private static final Map<String, ParameterInformationsData> TABLE = new TreeMap<>();
    /**
     * 参数表锁
     */
    private static final StampedLock TABLE_LOCK = new StampedLock();
    /**
     * 值
     */
    private final TreeMap<String, Object> data = new TreeMap<>(String::compareToIgnoreCase);
    /**
     * 值锁
     */
    private final StampedLock dataLock = new StampedLock();
    /**
     * 全局参数信息ID
     */
    private final String id;
    /**
     * 参数描述信息
     */
    private final String desciption;

    /**
     * 列出所有参数信息
     *
     * @param locale 当地位置
     * @return {@code List<AnyTypePairVal<String, String>>}
     */
    public static List<AnyTypePairVal<String, String>> listTable(Locale locale) {
        return Com.syncRead(TABLE_LOCK, () -> KitForMap.copyToList(TABLE, false, false, true
                , (k, v) -> AnyTypePairVal.of(k, v.forDesciption(locale)), ArrayList::new));
    }

    /**
     * 列出指定参数信息数据
     *
     * @param id     参数信息ID
     * @param locale 当地位置
     * @return {@code List<AnyTypeTripleVal<String, Object, String>>}
     */
    public static List<AnyTypeTripleVal<String, Object, String>> listTableData(String id, Locale locale) {
        ParameterInformationsData table = ParameterInformationsData.forParameterInformationsData(id);
        if (table == null) return Collections.emptyList();
        return KitForMap.copyToList(table.forData(), false, false, true,
                (k, v) -> AnyTypeTripleVal.of(k, v, table.forParameterDesciption(k, locale)), ArrayList::new);
    }

    /**
     * 列出指定参数信息数据
     *
     * @param locale 当地位置
     * @return {@code List<Map<String, Object>>}
     */
    public static List<Map<String, Object>> listTableData(Locale locale) {
        ArrayList<ParameterInformationsData> parameterInformationsData = forParameterInformationsData();
        return KitForMap.copyToMapList(parameterInformationsData, false, false, true,
                () -> new TreeMap<>(String::compareToIgnoreCase), () -> new ArrayList<>(10),
                (m, t) -> {
                    m.put("id", t.id);
                    m.put("idDesciption", t.forDesciption(locale));
                },
                (e, t) -> AnyTypePairVal.of("parameterName", e.name()),
                (e, t) -> AnyTypePairVal.of("parameterValue", t.forValueString(e.name())),
                (e, t) -> AnyTypePairVal.of("parameterDesciption", t.forParameterDesciption(e.name(), locale))
        );
    }

    /**
     * 获取指定ID的参数信息
     *
     * @param id 参数信息ID
     * @return {@code ParameterInformationsData}
     */
    public static ParameterInformationsData forParameterInformationsData(String id) {
        return id == null ? null : Com.syncRead(TABLE_LOCK, () -> TABLE.get(id));
    }

    /**
     * 获取指定ID的参数信息
     *
     * @return {@code ArrayList<ParameterInformationsData>}
     */
    public static ArrayList<ParameterInformationsData> forParameterInformationsData() {
        return Com.syncRead(TABLE_LOCK, () -> new ArrayList<>(TABLE.values()));
    }

    /**
     * 参数信息数据
     *
     * @param i18nBaseName i18n资源文件名
     * @param id           参数信息ID号
     * @param desciption   参数信息描述
     */
    public static ParameterInformationsData ofGlobal(String i18nBaseName, String id, String desciption) {
        return new ParameterInformationsData(true, i18nBaseName, id, desciption);
    }

    /**
     * 参数信息数据
     *
     * @param i18nBaseName i18n资源文件名
     * @param id           参数信息ID号
     * @param desciption   参数信息描述
     */
    public static ParameterInformationsData ofInner(String i18nBaseName, String id, String desciption) {
        return new ParameterInformationsData(false, i18nBaseName, id, desciption);
    }

    /**
     * 参数信息数据
     *
     * @param isGlobal     是否是全局参数
     * @param i18nBaseName i18n资源文件名
     * @param id           参数信息ID号
     * @param desciption   参数信息描述
     */
    protected ParameterInformationsData(boolean isGlobal, String i18nBaseName, String id, String desciption) {
        super(i18nBaseName);
        if (id == null) {
            throw new IllegalArgumentException("The id must be not null");
        }
        if (isGlobal && Com.syncOptimisticRead(TABLE_LOCK, () -> TABLE.containsKey(id))) {
            throw new IllegalArgumentException("The id '" + id + "' is already in use");
        }
        this.id = id;
        this.desciption = desciption;
        if (isGlobal) {
            Com.syncWrite(TABLE_LOCK, () -> TABLE.put(id, this));
        }
    }

    /**
     * 获取数据
     *
     * @return 返回数据
     */
    public Map<String, Object> forData() {
        return Com.syncOptimisticRead(dataLock, () -> new TreeMap<>(data));
    }

    /**
     * 获取参数信息表
     *
     * @param locale 当地位置
     * @return 返回参数信息表
     */
    public String forDesciption(Locale locale) {
        ResourceBundle resourceBundle = Com.safeGetResourceBundle(false, forI18nBaseName(), locale);
        if (resourceBundle != null && resourceBundle.containsKey(id)) {
            return resourceBundle.getString(id);
        }
        return desciption;
    }

    /**
     * 获取字符串值
     *
     * @param name 参数名称
     * @return 返回值
     */
    public String forValueString(String name) {
        Object value = this.forValue(name, "");
        return value == null ? "null" : value.toString();
    }

    /**
     * 获取字符串值
     *
     * @param name         参数名称
     * @param defaultValue 默认值
     * @return 返回值
     */
    public String forValueString(String name, Object defaultValue) {
        return String.valueOf(forValue(name, defaultValue));
    }

    /**
     * 获取值
     *
     * @param name         参数名称
     * @param defaultValue 默认值
     * @param <T>          值类型
     * @return 返回值
     */
    @SuppressWarnings("unchecked")
    public <T> T forValue(String name, Object defaultValue) {
        checkName(name);
        Object result, value = Com.syncOptimisticRead(dataLock, () -> data.get(name));
        if (value == null) value = defaultValue;
        if (value == null) return null;
        ParameterInformation parameterInformation = this.forParameterInformation(name);
        try {
            result = parameterInformation.type().cast(value);
        } catch (ClassCastException e) {
            if ("".equals(defaultValue)) result = null;
            else if (defaultValue == null) throw e;
            else result = value;
        }
        return (T) result;
    }

    /**
     * 设置值
     *
     * @param name  参数名称
     * @param value 参数值
     * @return {@link this}
     */
    public ParameterInformationsData setValue(String name, Object value) {
        checkName(name);
        ParameterInformation parameterInformation = this.forParameterInformation(name);
        if (!parameterInformation.isCompatibilityFrom(value)) {
            throw new IllegalArgumentException();
        }
        Com.syncWrite(dataLock, () -> data.put(name, value));
        return this;
    }

    @Override
    public ParameterInformationsData registerParameterWithCompatibilityType(String name, Class<?> type, String desciptionKey) {
        super.registerParameterWithCompatibilityType(name, type, desciptionKey);
        return this;
    }

    @Override
    public ParameterInformationsData registerParameterWithCompatibilityType(String name, Class<?> type, String desciptionKey, String desciption) {
        super.registerParameterWithCompatibilityType(name, type, desciptionKey, desciption);
        return this;
    }

    @Override
    public ParameterInformationsData registerParameterWithEqualsType(String name, Class<?> type, String desciptionKey) {
        super.registerParameterWithEqualsType(name, type, desciptionKey);
        return this;
    }

    @Override
    public ParameterInformationsData registerParameterWithEqualsType(String name, Class<?> type, String desciptionKey, String desciption) {
        super.registerParameterWithEqualsType(name, type, desciptionKey, desciption);
        return this;
    }

    private void checkName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("The name must be not null");
        }
        if ("id".equals(name)) {
            throw new IllegalArgumentException("The name must be not 'id'");
        }
        if (!this.containsParamter(name)) {
            throw new IllegalArgumentException("The current parameter table does not have a parameter configuration of '" + name + "'");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParameterInformationsData that = (ParameterInformationsData) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
