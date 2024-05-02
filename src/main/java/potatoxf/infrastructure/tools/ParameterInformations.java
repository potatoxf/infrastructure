package potatoxf.infrastructure.tools;

import potatoxf.infrastructure.Com;
import potatoxf.infrastructure.utils.KitForMap;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.locks.StampedLock;

/**
 * 参数信息容器，用于保存多个参数信息
 * <p>
 * <p/>
 * Create Time:2024-05-02
 *
 * @author potatoxf
 */
public class ParameterInformations implements Iterable<ParameterInformation>, Cloneable, Serializable {
    /**
     * i8n基础名称
     */
    private final String i18nBaseName;
    /**
     * 信息
     */
    private final TreeMap<String, ParameterInformation> info = new TreeMap<>(String::compareToIgnoreCase);
    /**
     * 信息锁
     */
    private final StampedLock infoLock = new StampedLock();

    public ParameterInformations(String i18nBaseName) {
        this.i18nBaseName = Objects.requireNonNull(i18nBaseName, "The table referenced by the internationalization information must not be null");
    }

    /**
     * 检查是否包含参数
     *
     * @param name 参数名称
     * @return 如果包含参数则返回true，否则返回false
     */
    public boolean containsParamter(String name) {
        return name != null && Com.syncOptimisticRead(infoLock, () -> info.containsKey(name));
    }

    public String forI18nBaseName() {
        return i18nBaseName;
    }

    /**
     * 获取所有参数名称
     *
     * @return {@code Set<ParameterInformation>}
     */
    public Set<String> forParamterNames() {
        return Com.syncOptimisticRead(infoLock, () -> new HashSet<>(info.keySet()));
    }

    /**
     * 获取参数描述
     *
     * @param name 参数名称
     * @return 返回参数描述
     */
    public String forParameterDesciption(String name) {
        return this.forParameterDesciption(name, Locale.getDefault());
    }

    /**
     * 获取参数描述
     *
     * @param name   参数名称
     * @param locale 当地位置
     * @return 返回参数描述
     */
    public String forParameterDesciption(String name, Locale locale) {
        ParameterInformation parameterInformation = forParameterInformation(name);
        if (parameterInformation != null) {
            return parameterInformation.desciption(i18nBaseName, locale);
        }
        return null;
    }

    /**
     * 获取参数描述
     *
     * @param locale 当地位置
     * @return {@code Map<String, String>}
     */
    public Map<String, String> forParameterDesciptionMap(Locale locale) {
        return Com.syncOptimisticRead(infoLock, () -> KitForMap.copyToMap(info, true, true, true
                , k -> k, v -> v.desciption(i18nBaseName, locale), i -> new TreeMap<>(String::compareToIgnoreCase)));
    }

    /**
     * 获取参数信息
     *
     * @param name 参数名称
     * @return {@code ParameterInformation}
     */
    public ParameterInformation forParameterInformation(String name) {
        return Com.syncOptimisticRead(infoLock, () -> info.get(name));
    }

    /**
     * 获取参数信息
     *
     * @return {@code Set<ParameterInformation>}
     */
    public Set<ParameterInformation> forParameterInformations() {
        return Com.syncOptimisticRead(infoLock, () -> new HashSet<>(info.values()));
    }

    /**
     * 获取所有参数名称
     *
     * @return {@code Map<String, ParameterInformation>}
     */
    public Map<String, ParameterInformation> forParameterInformationMap() {
        return Com.syncOptimisticRead(infoLock, () -> KitForMap.copyToMap(info, false, false, true
                , k -> k, v -> v, i -> new TreeMap<>(String::compareToIgnoreCase)));
    }

    /**
     * 注册兼容参数
     *
     * @param name          参数名称
     * @param type          参数类型
     * @param desciptionKey 描述key
     * @return {@link this}
     */
    public ParameterInformations registerParameterWithCompatibilityType(String name, Class<?> type, String desciptionKey) {
        return this.registerParameter(name, type, true, desciptionKey, null);
    }

    /**
     * 注册兼容参数
     *
     * @param name          参数名称
     * @param type          参数类型
     * @param desciptionKey 描述key
     * @param desciption    描述
     * @return {@link this}
     */
    public ParameterInformations registerParameterWithCompatibilityType(String name, Class<?> type, String desciptionKey, String desciption) {
        return this.registerParameter(name, type, true, desciptionKey, desciption);
    }

    /**
     * 注册等值参数
     *
     * @param name          参数名称
     * @param type          参数类型
     * @param desciptionKey 描述key
     * @return {@link this}
     */
    public ParameterInformations registerParameterWithEqualsType(String name, Class<?> type, String desciptionKey) {
        return this.registerParameter(name, type, false, desciptionKey, null);
    }

    /**
     * 注册等值参数
     *
     * @param name          参数名称
     * @param type          参数类型
     * @param desciptionKey 描述key
     * @param desciption    描述
     * @return {@link this}
     */
    public ParameterInformations registerParameterWithEqualsType(String name, Class<?> type, String desciptionKey, String desciption) {
        return this.registerParameter(name, type, false, desciptionKey, desciption);
    }

    /**
     * 注册参数
     *
     * @param name          参数名称
     * @param type          参数类型
     * @param compatibility 是否兼容
     * @param desciptionKey 描述key
     * @param desciption    描述
     * @return {@link this}
     */
    private ParameterInformations registerParameter(String name, Class<?> type, boolean compatibility, String desciptionKey, String desciption) {
        Objects.requireNonNull(name, "The parameter name must be not null");
        Objects.requireNonNull(type, "The parameter type must be not null");
        Com.syncWrite(infoLock, () -> info.put(name, new ParameterInformation(name, type, compatibility, desciptionKey, desciption)));
        return this;
    }

    @Override
    public Iterator<ParameterInformation> iterator() {
        return forParameterInformations().iterator();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return Com.buildToString("ParameterInformations", "i18nBaseName", i18nBaseName, "Paramters", forParameterInformations());
    }
}
