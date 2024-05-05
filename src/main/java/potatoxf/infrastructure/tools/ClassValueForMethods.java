package potatoxf.infrastructure.tools;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取{@link Class}的{@link Method[]}
 * <p/>
 * Create Time:2024-05-05
 *
 * @author potatoxf
 */
public class ClassValueForMethods extends ClassValue<Method[]> {
    private final boolean includeSuperclass;
    private final boolean includeStatic;
    private final boolean includePrivate;

    public ClassValueForMethods(boolean includeSuperclass, boolean includeStatic, boolean includePrivate) {
        this.includeSuperclass = includeSuperclass;
        this.includeStatic = includeStatic;
        this.includePrivate = includePrivate;
    }

    @Override
    protected Method[] computeValue(Class<?> clz) {
        List<Method> results = new ArrayList<>();
        for (Class<?> c = clz; c != Object.class; c = c.getSuperclass()) {
            for (Method e : c.getDeclaredMethods()) {
                int modifiers = e.getModifiers();
                if (!includeStatic && Modifier.isStatic(modifiers)) continue;
                if (!includePrivate && !Modifier.isPublic(modifiers)) continue;

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
        return results.toArray(new Method[0]);
    }
}