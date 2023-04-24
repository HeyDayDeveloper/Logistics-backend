package cn.anselyuki.controller;

import cn.anselyuki.controller.request.UserLoginDTO;
import cn.anselyuki.controller.request.UserRegisterDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;
import java.util.Date;

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
    public ResponseEntity<Result<LoginResponse>> login(@RequestBody UserLoginDTO user) throws LoginException {
        return userService.login(user);
    }

    /**
     * 用户注册
     * 必须传入用户名与密码，其他信息可选
     *
     * @param user 用户信息
     * @return 注册成功的用户信息
     */
    @PostMapping("register")
    public ResponseEntity<Result<Object>> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        User user = new User(userRegisterDTO);
        if (userRepository.existsByUsername(user.getUsername())) {
            return Result.fail(409, "用户名已存在");
        }
        String encode = new BCryptPasswordEncoder().encode(user.getPassword());
        //设置id为空,防止前端传入id,用于生成UUID
        user.setId(null);
        //注册接口默认注册为普通用户
        user.setType(1);
        //密码加密
        user.setPassword(encode);
        //设置创建时间
        user.setCreateTime(new Date());
        //设置用户状态
        user.setStatus(1);
        userRepository.save(user);
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(user, userInfoVO);
        return Result.success(userInfoVO);
    }
}
