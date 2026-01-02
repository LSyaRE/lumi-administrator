package com.luminesway.concursoadminstrator.shared.utils;

import com.luminesway.concursoadminstrator.shared.exceptions.LumiMappingException;

/**
 * The Mapping interface defines a contract for transforming an input object of one type
 * into an output object of another type, based on their fields. It provides a mechanism
 * for mapping fields between objects and allows for configuring how null values are handled.
 */
public interface Mapping {
    /**
     * Transforms the fields of the source object into the fields newly created
     * instance of the target class, based on matching field names. Fields that do not
     * exist in the target class are ignored. By default, null values are excluded unless
     * explicitly allowed by configuration.
     *
     * @param <T> the type of the resulting object
     * @param from the source object whose fields are to be copied to the target object
     * @param target the class type of the target object to be created and populated
     * @return an instance of the target class with its fields populated from the source object
     * @throws Exception if instantiation of the target object or field access fails
     */
    public <T> T execute(Object from, Class<T> target) throws LumiMappingException;
    /**
     * Configures whether null values should be allowed during the mapping process.
     *
     * @param isNullable a boolean indicating if null values should be permitted.
     *                   If true, null values from the source object will be mapped to
     *                   corresponding fields in the target object. If false, null values
     *                   will be excluded during the mapping.
     */
    public void setNullabe(boolean isNullable);
}
