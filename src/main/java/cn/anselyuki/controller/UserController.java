package cn.anselyuki.controller;

import cn.anselyuki.controller.response.Result;
import cn.anselyuki.controller.response.UserInfoVO;
import cn.anselyuki.repository.UserRepository;
import cn.anselyuki.repository.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.util.BeanDefinitionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author AnselYuki
 */
@Tag(name = "User")
@RequestMapping("user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @PostMapping("add")
    public Result<UserInfoVO> add(@RequestBody User user) {
        userRepository.save(user);
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(user, userInfoVO);
        return Result.success(userInfoVO);
    }
}
