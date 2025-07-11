package com.luminesway.concursoadminstrator.shared.utils;

import lombok.Builder;

import java.util.List;

@Builder
public record ResultParameters<T>(T result, String message, List<String> errors) {

}
