package org.davidescobarr.quizbackend.util.security;

import java.lang.reflect.Field;

public class SecurityFieldProcessor {
    public static void secureFields(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(SecurityField.class)) {
                field.setAccessible(true);
                field.set(obj, null);
            }
        }
    }
}
