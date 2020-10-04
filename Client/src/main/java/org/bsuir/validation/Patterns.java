package org.bsuir.validation;

public final class Patterns {

    public static final String EMAIL_PATTERN =
            "(\\w{1,15}[\\.-]?\\w{1,15}[a-z]{1,10})" +
            "+@" +
            "([A-Za-z]{2,4}[\\.]{1}[a-z]{2,4})";
    public static final String PRODUCER_PRODUCT_NAME_PATTERN =
            "\\w{3,30}[0-9]?\\w{0,15}[\\.-]?\\w{0,15}";
    public static final String PRODUCER_NUMBER_PATTERN =
            "[\\+]?[0-9]{5,15}";
    public static final String USER_NAME_PATTERN =
            "[A-Za-zА-Яа-я ]{1,30}";
}
