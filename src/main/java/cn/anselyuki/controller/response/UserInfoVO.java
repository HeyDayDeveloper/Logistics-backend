package cn.anselyuki.controller.response;

import cn.anselyuki.repository.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @author AnselYuki
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserInfoVO {
    private String nickname;
    private String email;
    private String phoneNumber;
    private Integer status;
    private String avatar;

    public UserInfoVO(User user) {
        BeanUtils.copyProperties(user, this);
    }
}
