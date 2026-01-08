package com.luminesway.concursoadminstrator.modules.auth.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum BloodType {
    O_NEGATIVE("O-"),
    O_POSITIVE("O+"),
    A_NEGATIVE("A-"),
    A_POSITIVE("A+"),
    B_NEGATIVE("B-"),
    B_POSITIVE("B+"),
    AB_NEGATIVE("AB-"),
    AB_POSITIVE("AB+");

    private final String label;

    BloodType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
    public static List<String> getAllLabels() {
        return Arrays.stream(BloodType.values())
                .map(BloodType::getLabel)
                .collect(Collectors.toList());
    }
}