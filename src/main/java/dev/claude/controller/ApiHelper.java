package dev.claude.controller;

import dev.claude.dto.ApiMessageDTO;

public class ApiHelper {
    public static final int OK = 200;
    public static final int CREATED = 201;

    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int NOT_FOUND = 404;

    public static final int INTERNAL_ERROR = 500;
    public static final int NOT_IMPLEMENTED = 501;

    private static String getTypeFromCode(int code) {
        switch (code) {
            case CREATED:
                return "Created";
            case OK:
                return "OK";

            case BAD_REQUEST:
                return "Bad Request";
            case UNAUTHORIZED:
                return "Unauthorized";
            case NOT_FOUND:
                return "Not Found";

            case INTERNAL_ERROR:
                return "Internal Server Error";
            case NOT_IMPLEMENTED:
                return "Not Implemented";

            default:
                return "unknown";
        }
    }

    public static ApiMessageDTO createApiMessage(int code, String message) {
        ApiMessageDTO apiMessageDTO = new ApiMessageDTO();
        apiMessageDTO.setCode(code);
        apiMessageDTO.setType(getTypeFromCode(code));
        apiMessageDTO.setMessage(message);
        return apiMessageDTO;
    }

    public static ApiMessageDTO ok(String message) {
        return createApiMessage(OK, message);
    }

    public static ApiMessageDTO created(String message) {
        return createApiMessage(CREATED, message);
    }

    public static ApiMessageDTO error(int code, String message) {
        return createApiMessage(code, message);
    }

}
