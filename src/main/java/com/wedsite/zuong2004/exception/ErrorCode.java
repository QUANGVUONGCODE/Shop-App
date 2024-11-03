package com.wedsite.zuong2004.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNICATEGORIZE_EXCEPTION(9999, "ERROR", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1002, "Invalid key", HttpStatus.BAD_REQUEST),
    INVALID_ID(1003, "Invalid id", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1004, "You do not have permission", HttpStatus.FORBIDDEN),
    NAME_INVALID(1005, "Product name length must be between 3 and 200 characters", HttpStatus.BAD_REQUEST),
    PRICE_MIN(1006, "Price must be greater than or equal to 0", HttpStatus.BAD_REQUEST),
    PRICE_MAX(1007, "Price must be less than or equal to 10000000", HttpStatus.BAD_REQUEST),
    SIZE_IMAGES(1008, "Number of images must be between 1 and 5", HttpStatus.BAD_REQUEST),
    INVALID_IMAGES(1009, "Invalid images", HttpStatus.BAD_REQUEST),
    IMAGE_MAX_SIZE(1010, "Image size must be less than or equal to 10MB", HttpStatus.BAD_REQUEST),
    INVALID_FILE(1011, "File is not an image!", HttpStatus.BAD_REQUEST),
    NOT_BLANK(1012, "Not blank", HttpStatus.BAD_REQUEST),
    ROLE_ID_REQUIRED(1013, "Role id is required", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_INVALID(1014, "Phone number invalid", HttpStatus.BAD_REQUEST),
    INVALID_ROLE_ID(1015, "Invalid role id", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1016, "Password invalid", HttpStatus.BAD_REQUEST),
    ADDRESS_INVALID(1017, "Address invalid", HttpStatus.BAD_REQUEST),
    NOT_USER(1018, "Not user", HttpStatus.NOT_FOUND),
    INVALID_DATE(1019, "Invalid date", HttpStatus.BAD_REQUEST),
    INVALID_USER_WITH_PHONE_NUMBER(1020, "Invalid user with phone number", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1021, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;

}
