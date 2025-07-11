package com.luminesway.concursoadminstrator.shared.utils;

public interface Mapping {
    public <T> T execute(Object from, Class<T> target) throws Exception;
    public void setNullabe(boolean isNullable);
}
