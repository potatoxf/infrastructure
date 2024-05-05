package potatoxf.infrastructure.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取{@link Class}的{@link Field[]}
 * <p/>
 * Create Time:2024-05-05
 *
 * @author potatoxf
 */
public class ClassValueForFields extends ClassValue<Field[]> {
    private final boolean includeSuperclass;
    private final boolean includeStatic;
    private final boolean includePrivate;
    private final boolean includePrimitive;

    public ClassValueForFields(boolean includeSuperclass, boolean includeStatic, boolean includePrivate, boolean includePrimitive) {
        this.includeSuperclass = includeSuperclass;
        this.includeStatic = includeStatic;
        this.includePrivate = includePrivate;
        this.includePrimitive = includePrimitive;
    }

    @Override
    protected Field[] computeValue(Class<?> clz) {
        List<Field> results = new ArrayList<>();
        for (Class<?> c = clz; c != Object.class; c = c.getSuperclass()) {
            for (Field e : c.getDeclaredFields()) {
                int modifiers = e.getModifiers();
                if (!includeStatic && Modifier.isStatic(modifiers)) continue;
                if (!includePrivate && !Modifier.isPublic(modifiers)) continue;
                if (!includePrimitive && e.getType().isPrimitive()) continue;

                if (includePrivate && !Modifier.isPublic(modifiers)) {
                    try {
                        e.setAccessible(true);
                    } catch (Throwable ex) {
                        // No biggie, walker code would try something else.
                    }
                }
                results.add(e);
            }
            if (!includeSuperclass) break;
        }
        return results.toArray(new Field[0]);
    }
}