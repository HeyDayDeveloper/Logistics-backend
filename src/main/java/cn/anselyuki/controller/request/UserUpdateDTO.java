package cn.anselyuki.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserUpdateDTO {
    private String id;
    private String username;
    private String nickname;
    private String email;
    private String phoneNumber;
    private Integer status;
    private Date createTime;
    private Date modifiedTime;
    private Boolean sex;
    private Integer type;
    private Date birth;
    private String avatar;
}
