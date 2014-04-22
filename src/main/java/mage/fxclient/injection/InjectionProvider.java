package mage.fxclient.injection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author North
 */
public class InjectionProvider {

    private final Map<Class, Object> dependencies = new HashMap<>();

    public <T> void addDependency(Class<T> clazz, T object) {
        dependencies.put(clazz, object);
    }

    public Object createInstance(Class<?> clazz) {
        try {
            Object instance = clazz.newInstance();

            injectMembers(clazz, instance);
            invokeMethodWithAnnotation(clazz, instance, PostConstruct.class);

            return instance;
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new IllegalStateException("Cannot instantiate controller: " + clazz, ex);
        }

    }

    private void injectMembers(Class<? extends Object> clazz, final Object instance) throws SecurityException {
        Field[] fields = clazz.getDeclaredFields();
        for (final Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                Class<?> type = field.getType();
                Object value = dependencies.get(type);
                if (value != null) {
                    injectIntoField(field, instance, value);
                }
            }
        }

        Class<? extends Object> superclass = clazz.getSuperclass();
        if (superclass != null) {
            injectMembers(superclass, instance);
        }
    }

    private void injectIntoField(final Field field, final Object instance, final Object target) {
        AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
            boolean wasAccessible = field.isAccessible();
            try {
                field.setAccessible(true);
                field.set(instance, target);
                return null;
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                throw new IllegalStateException("Cannot set field: " + field, ex);
            } finally {
                field.setAccessible(wasAccessible);
            }
        });
    }

    private void invokeMethodWithAnnotation(Class clazz, final Object instance, final Class<? extends Annotation> annotationClass) throws IllegalStateException, SecurityException {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (final Method method : declaredMethods) {
            if (method.isAnnotationPresent(annotationClass) && method.getParameterCount() == 0) {
                AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
                    boolean wasAccessible = method.isAccessible();
                    try {
                        method.setAccessible(true);
                        return method.invoke(instance, new Object[]{});
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        throw new IllegalStateException("Problem invoking " + annotationClass + " : " + method, ex);
                    } finally {
                        method.setAccessible(wasAccessible);
                    }
                });
            }
        }

        Class superclass = clazz.getSuperclass();
        if (superclass != null) {
            invokeMethodWithAnnotation(superclass, instance, annotationClass);
        }
    }
}
