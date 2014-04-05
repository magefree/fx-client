package mage.fxclient;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Callback;
import javax.inject.Inject;

/**
 *
 * @author North
 */
public class ControllerFactory implements Callback<Class<?>, Object> {

    private final Map<Class, Object> dependencies = new HashMap<>();

    public <T> void addDependency(Class<T> clazz, T object) {
        dependencies.put(clazz, object);
    }

    @Override
    public Object call(Class<?> clazz) {
        try {
            Object instance = clazz.newInstance();

            injectMembers(instance);

            return instance;
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new IllegalStateException("Cannot instantiate controller: " + clazz, ex);
        }
    }

    private void injectMembers(Object instance) {
        Class<? extends Object> clazz = instance.getClass();
        injectMembers(clazz, instance);
    }

    private void injectMembers(Class<? extends Object> clazz, final Object instance) throws SecurityException {
        Field[] fields = clazz.getDeclaredFields();
        for (final Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                Class<?> type = field.getType();
                Object target = dependencies.get(type);

                injectIntoField(field, instance, target);
            }
        }
        Class<? extends Object> superclass = clazz.getSuperclass();
        if (superclass != null) {
            injectMembers(superclass, instance);
        }
    }

    private void injectIntoField(final Field field, final Object instance, final Object target) {
        AccessController.doPrivileged((PrivilegedAction) () -> {
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
}
