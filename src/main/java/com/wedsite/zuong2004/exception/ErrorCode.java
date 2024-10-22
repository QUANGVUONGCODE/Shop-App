package com.wedsite.zuong2004.exception;

public enum ErrorCode {
    UNICATEGORIZE_EXCEPTION(9999, "ERROR"),
    USER_EXISTED(1001, "User existed"),
    INVALID_KEY(1002, "Invalid key"),
    INVALID_ID(1003, "Invalid id"),
    UNAUTHORIZED(1004, "Unauthorized"),
    NAME_INVALID(1005, "Product name length must be between 3 and 200 characters"),
    PRICE_MIN(1006, "Price must be greater than or equal to 0"),
    PRICE_MAX(1007, "Price must be less than or equal to 10000000"),
    SIZE_IMAGES(1008, "Number of images must be between 1 and 5"),
    INVALID_IMAGES(1009, "Invalid images"),
    IMAGE_MAX_SIZE(1010, "Image size must be less than or equal to 10MB"),
    INVALID_FILE(1011, "File is not an image!"),
    NOT_BLANK(1012, "Not blank"),
    ROLE_ID_REQUIRED(1013, "Role id is required"),
    PHONE_NUMBER_INVALID(1014, "Phone number invalid"),
    INVALID_ROLE_ID(1015, "Invalid role id"),
    PASSWORD_INVALID(1016, "Password invalid"),
    ADDRESS_INVALID(1017, "Address invalid"),
    NOT_USER(1018, "Not user"),
    INVALID_DATE(1019, "Invalid date"),
    INVALID_USER_WITH_PHONE_NUMBER(1020, "Invalid user with phone number"),
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
