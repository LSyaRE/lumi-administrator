package com.luminesway.concursoadminstrator.config;

import org.springframework.beans.factory.annotation.Value;

public class SecurityValues {
    @Value( "${api.url.front}")
    public static final String URL_FRONTEND = "";
}
