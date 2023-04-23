package cn.anselyuki.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @author AnselYuki
 * @date 2023/4/23 16:17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Result<T>(int statusCode, String message, T data) implements Serializable {
    public static <T> Result<T> success(T data) {
        return success(null, data);
    }

    public static <T> Result<T> success() {
        return success(null, null);
    }

    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(200, msg, data);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return fail(code, msg, null);
    }

    public static <T> Result<T> fail(int code, String msg, T data) {
        return new Result<>(code, msg, data);
    }
}