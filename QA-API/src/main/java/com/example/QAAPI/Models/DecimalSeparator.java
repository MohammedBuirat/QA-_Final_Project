package com.example.QAAPI.Models;

public enum DecimalSeparator {
    COMMA,
    POINT;
    public static DecimalSeparator Parse(String input) {
        if ("0".equals(input)) {
            return COMMA;
        }
        return POINT;
    }
}