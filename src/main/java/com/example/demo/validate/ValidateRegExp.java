package com.example.demo.validate;

import java.util.EnumSet;

public enum ValidateRegExp {

    REG_EXP_1("[0-9_.]+"),
    REG_EXP_2("[\\w]+");

    private final String value;

    ValidateRegExp(final String value) {
        this.value = value;
    }

    public String getRegExp() {
        return value;
    }

    public static ValidateRegExp getRegExpValidate(final String validateString) {
        return EnumSet.allOf(ValidateRegExp.class)
                .stream()
                .filter(obj -> validateString.matches(obj.getRegExp()))
                .findFirst()
                .get();
    }
}
