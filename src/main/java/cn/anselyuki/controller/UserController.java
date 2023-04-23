package cn.anselyuki.controller;

import cn.anselyuki.controller.request.userLoginDTO;
import cn.anselyuki.controller.response.LoginResponse;
import cn.anselyuki.controller.response.Result;
import cn.anselyuki.controller.response.UserInfoVO;
import cn.anselyuki.repository.UserRepository;
import cn.anselyuki.repository.entity.User;
import cn.anselyuki.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;

/**
 * @author AnselYuki
 */
@Tag(name = "User")
@RequestMapping("user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("login")
    @Operation(summary = "用户登录", description = "接受用户名与密码，返回token")
    public Result<LoginResponse> login(@RequestBody userLoginDTO user) throws LoginException {
        return userService.login(user);
    }

    @PostMapping("add")
    public Result<UserInfoVO> add(@RequestBody User user) {
        userRepository.save(user);
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(user, userInfoVO);
        return Result.success(userInfoVO);
    }
}
