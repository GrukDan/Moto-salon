package org.bsuir.validation;

public final class Patterns {

    public static final String EMAIL_PATTERN =
            "(\\w{1,15}[\\.-]?\\w{1,15}[a-z]{1,10})" +
            "+@" +
            "([A-Za-z]{2,4}[\\.]{1}[a-z]{2,4})";
    public static final String PRODUCER_PRODUCT_NAME_PATTERN =
            "^[а-яА-ЯёЁa-zA-Z0-9-_\\.]{2,20}\\s?[а-яА-ЯёЁa-zA-Z0-9-_\\.]{2,20}$";
    public static final String PRODUCER_NUMBER_PATTERN =
            "[\\+]?[0-9]{5,15}";
    public static final String USER_NAME_PATTERN =
            "^[а-яА-ЯёЁa-zA-Z-\\.]{2,20}[\\s]{0,1}[а-яА-ЯёЁa-zA-Z0-9-_\\.]{2,20}$";
}
