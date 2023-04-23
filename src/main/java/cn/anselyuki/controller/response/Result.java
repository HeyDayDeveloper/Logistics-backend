package cn.anselyuki.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

/**
 * @author AnselYuki
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Result<T>(int statusCode, String message, T data) implements Serializable {
    /**
     * 有参构造,需要全部参数
     *
     * @param msg  消息
     * @param data 数据
     * @param <T>  泛型
     * @return Result
     */
    public static <T> ResponseEntity<Result<T>> success(String msg, T data) {
        return ResponseEntity.ok(new Result<>(200, msg, data));
    }

    /**
     * 有参构造,仅携带数据
     *
     * @param data 消息
     * @param <T>  泛型
     * @return Result
     */
    public static <T> ResponseEntity<Result<T>> success(T data) {
        return success(null, data);
    }

    /**
     * 不返回任何字段
     *
     * @param <T> 泛型
     * @return Result
     */
    public static <T> ResponseEntity<Result<T>> success() {
        return success(null, null);
    }

    /**
     * 失败响应，仅携带消息
     *
     * @param code 状态码
     * @param msg  消息
     * @param <T>  泛型
     * @return ResponseEntity
     */
    public static <T> ResponseEntity<Result<T>> fail(int code, String msg) {
        return fail(code, msg, null);
    }

    /**
     * 失败响应，携带消息与数据
     *
     * @param code 状态码
     * @param msg  消息
     * @param data 数据
     * @param <T>  泛型
     * @return ResponseEntity
     */
    public static <T> ResponseEntity<Result<T>> fail(int code, String msg, T data) {
        return ResponseEntity.status(code).body(new Result<>(code, msg, data));
    }
}