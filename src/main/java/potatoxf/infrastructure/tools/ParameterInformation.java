package potatoxf.infrastructure.tools;

import potatoxf.infrastructure.Arg;
import potatoxf.infrastructure.Com;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * 参数信息，用于保存参数相关信息
 * <p/>
 * Create Time:2024-05-02
 *
 * @author potatoxf
 */
public class ParameterInformation implements Cloneable, Serializable {
    /**
     * 参数名称
     */
    private final String name;
    /**
     * 参数类型
     */
    private final Class<?> type;
    /**
     * 参数兼容性
     */
    private final boolean compatibility;
    /**
     * 参数描述键
     */
    private final String desciptionKey;
    /**
     * 参数描述信息
     */
    private final String desciption;

    public ParameterInformation(String name, Class<?> type, boolean compatibility, String desciptionKey, String desciption) {
        this.name = name;
        this.type = type;
        this.compatibility = compatibility;
        this.desciptionKey = desciptionKey;
        this.desciption = desciption;
    }

    /**
     * 确定此类的参数类型是否兼容传入参数类型
     *
     * @param parameterInformation 参数信息
     * @return 如果兼容返回true，否则返回false
     */
    public boolean isCompatibilityFrom(ParameterInformation parameterInformation) {
        return parameterInformation != null && this.compatibilityFrom(parameterInformation.type);
    }

    /**
     * 确定此类的参数类型是否兼容传入参数类型
     *
     * @param parameterType 参数类型
     * @return 如果兼容返回true，否则返回false
     */
    public boolean isCompatibilityFrom(Class<?> parameterType) {
        return parameterType != null && this.compatibilityFrom(parameterType);
    }

    /**
     * 确定此类的参数是否兼容传入参数类型
     *
     * @param parameter 参数
     * @return 如果兼容返回true，否则返回false
     */
    public boolean isCompatibilityFrom(Object parameter) {
        return parameter != null && this.compatibilityFrom(parameter.getClass());
    }

    /**
     * 确定此类的参数类型是否兼容传入参数类型
     *
     * @param parameterType 参数类型
     * @return 如果兼容返回true，否则返回false
     */
    private boolean compatibilityFrom(Class<?> parameterType) {
        if (!compatibility) {
            return parameterType == type;
        } else {
            return Arg.isCompatibilityFrom(parameterType, type);
        }
    }

    /**
     * 获取描述
     *
     * @param i18nBaseName 国际化语言资源包基础名
     * @param locale       国际化语言位置
     * @return 返回描述
     */
    public String desciption(String i18nBaseName, Locale locale) {
        return this.desciption(Com.safeGetResourceBundle(false, i18nBaseName, locale));
    }

    /**
     * 获取描述
     *
     * @param resourceBundle i18语言资源包
     * @return 返回描述
     */
    public String desciption(ResourceBundle resourceBundle) {
        if (resourceBundle != null) {
            if (desciptionKey != null && resourceBundle.containsKey(desciptionKey)) {
                return resourceBundle.getString(desciptionKey);
            }
            if (name != null && resourceBundle.containsKey(name)) {
                return resourceBundle.getString(name);
            }
        }
        return desciption;
    }

    /**
     * 获取参数类型
     *
     * @return 返回参数类型
     */
    public String name() {
        return name;
    }

    /**
     * 获取参数名称
     *
     * @return 返回参数名称
     */
    public Class<?> type() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParameterInformation that = (ParameterInformation) o;
        return compatibility == that.compatibility && Objects.equals(name, that.name) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, compatibility);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return Com.buildToString("ParameterInformation", "ParameterName", name, "ParameterType", type, "ParameterCompatibility", compatibility);
    }
}
