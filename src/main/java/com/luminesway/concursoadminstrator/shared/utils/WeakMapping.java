package com.luminesway.concursoadminstrator.shared.utils;


import com.luminesway.concursoadminstrator.shared.exceptions.LumiMappingException;

import java.lang.reflect.Field;
import java.util.logging.Logger;

public class WeakMapping implements Mapping {

    private static final Logger log = Logger.getLogger(WeakMapping.class.getName());
    boolean isNullAllowed = false;

    @Override
    public <T> T execute(Object from, Class<T> target) throws LumiMappingException {
        try {
            T targetInstance = target.getDeclaredConstructor().newInstance();
            Class<?> clazz = from.getClass();

            Field[] fromFields = clazz.getDeclaredFields();

            for (Field originField : fromFields) {
                originField.setAccessible(true);

                try {
                    Field targetField = target.getDeclaredField(originField.getName());
                    targetField.setAccessible(true);

                    Object value = originField.get(from);
                    if (isNullAllowed || value != null) {
                        targetField.set(targetInstance, value);
                    }
                } catch (NoSuchFieldException e) {
                    log.warning("The field " + originField.getName() + " does not exist in the target class " + target.getName() + ". Ignoring it.");
                }
            }

            return targetInstance;
        } catch (Exception e) {
            throw new LumiMappingException("Error mapping object of type " + from.getClass().getName() + " to " + target.getName(), e);
        }
    }

    @Override
    public void setNullabe(boolean isNullable) {
        this.isNullAllowed = isNullable;
    }
}
