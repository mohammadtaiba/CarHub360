package de.fherfurt.testsupport;

import java.lang.reflect.Field;

public final class InjectionSupport {

    private InjectionSupport() {
    }

    public static void inject(Object target, String fieldName, Object value) {
        Class<?> type = target.getClass();
        while (type != null) {
            try {
                Field field = type.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(target, value);
                return;
            } catch (NoSuchFieldException ignored) {
                type = type.getSuperclass();
            } catch (IllegalAccessException exception) {
                throw new IllegalStateException("Could not inject field " + fieldName, exception);
            }
        }
        throw new IllegalArgumentException("Field not found: " + fieldName);
    }
}
