package com.library.util;

public final class ValidationUtils {
    private ValidationUtils() {
    }

    public static void required(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " is required.");
        }
    }

    public static void positive(int value, String field) {
        if (value <= 0) {
            throw new IllegalArgumentException(field + " must be greater than zero.");
        }
    }

    public static void email(String value) {
        required(value, "Email");
        if (!value.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email address.");
        }
    }
}
