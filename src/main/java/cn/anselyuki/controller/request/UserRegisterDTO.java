package cn.anselyuki.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

/**
 * @author AnselYuki
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserRegisterDTO {
    @NotBlank(message = "用户名不能为空")
    private String username;
    private String nickname;
    @NotBlank(message = "密码不能为空")
    private String password;
    private String email;
    private String phoneNumber;
    private Boolean sex;
    private Integer type;
    private Date birth;
    private String avatar;
}
