package ru.cootrip.api.common.jwt.enumeration;

public enum TokenType {

    ACCESS,

    REFRESH;

    public static String getClaimKey() {
        return TokenType.class.getSimpleName();
    }

}
