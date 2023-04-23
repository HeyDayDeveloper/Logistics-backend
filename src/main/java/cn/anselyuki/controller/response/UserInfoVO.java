package cn.anselyuki.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author AnselYuki
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserInfoVO {
    private String nickname;
    private String email;
    private String phoneNumber;
    private Integer status;
    private String avatar;
}
