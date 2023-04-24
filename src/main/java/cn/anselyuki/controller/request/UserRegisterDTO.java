package cn.anselyuki.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @author AnselYuki
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserRegisterDTO {
    private String username;
    private String nickname;
    private String email;
    private String phoneNumber;
    private Boolean sex;
    private Integer type;
    private Date birth;
    private String avatar;
}
