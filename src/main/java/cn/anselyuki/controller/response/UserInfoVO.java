package cn.anselyuki.controller.response;

import cn.anselyuki.repository.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author AnselYuki
 */
@Data
@NoArgsConstructor
public class UserInfoVO {
    private String id;
    private String username;
    private String nickname;
    private String email;
    private String phoneNumber;
    private Integer status;
    private Date createTime;
    private Date modifiedTime;
    private Integer sex;
    private Integer type;
    private Date birth;
    private String avatar;

    public UserInfoVO(User user) {
        BeanUtils.copyProperties(user, this);
    }
}
