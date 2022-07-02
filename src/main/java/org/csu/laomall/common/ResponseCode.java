package org.csu.laomall.common;

import lombok.Getter;

@Getter
public enum ResponseCode {

    SUCCESS(20, "Success"),
    ERROR(10, "Error"),
    NEED_LOGIN(41, "Need Login"),
    ILLEGAL_ARGUMENT(40, "Illegal Argument"),
    NO_DATA_FOUND(42, "No Data Found"),
    PERMISSION_DENIED(43, "Permission Denied"),
    INTERNAL_SERVER_ERROR(50, "Internal Server Error");

    private final int code;
    private final String description;

    ResponseCode(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
