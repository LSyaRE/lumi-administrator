package com.luminesway.concursoadminstrator.modules.core.services.documents;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface DocumentGenerator {
    public Object generate(String fileName, Map<String, String> data);
}


