package com.luminesway.concursoadminstrator.config;

import org.springframework.beans.factory.annotation.Value;

public class SecurityValues {
    @Value( "${api.url.front}")
    public static String URL_FRONTEND = "";
    public static String URL_FRONTEND_CONTEST = "";

}
