package com.dev.uploadImage.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BasicResponse {

    public Map<String, Object> successResponse(Integer code, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", Objects.requireNonNullElse(code, 200));
        response.put("message", Objects.requireNonNullElse(message, "success"));
        response.put("data", data);
        return response;
    }

    public Map<String, Object> errorResponse(Integer code, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", Objects.requireNonNullElse(code, 801));
        response.put("message", Objects.requireNonNullElse(message, "error"));
        response.put("data", data);
        return response;
    }
}
