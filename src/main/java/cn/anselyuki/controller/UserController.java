package cn.anselyuki.controller;

import cn.anselyuki.common.annotation.AccessLimit;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import java.util.Date;
import java.util.List;

/**
 * @author AnselYuki
 */
@Slf4j
@Tag(name = "User", description = "用户相关接口")
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
     * @param userRegisterDTO 用户信息
     * @return 注册成功的用户信息
     */
    @AccessLimit(times = 5, second = 15)
    @PostMapping("register")
    @Operation(summary = "用户注册", description = "接受用户名与密码，返回用户信息")
    public ResponseEntity<Result<Object>> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        User user = new User(userRegisterDTO);
        String encode = new BCryptPasswordEncoder().encode(user.getPassword());
        //设置id为空,防止前端传入id,用于生成UUID,注册接口默认注册为普通用户
        user.setId(null).setType(1).setPassword(encode).setCreateTime(new Date()).setStatus(1);
        UserInfoVO userInfo;
        try {
            User save = userRepository.save(user);
            userInfo = new UserInfoVO(save);
        } catch (Exception e) {
            return Result.fail(409, "用户名已存在");
        }
        return Result.success(userInfo);
    }

    /**
     * 获取用户列表
     *
     * @return 用户列表
     */
    @GetMapping("list")
    @Operation(summary = "获取用户列表", description = "获取用户列表")
    public ResponseEntity<Result<List<User>>> list() {
        return Result.success(userRepository.findAll());
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "删除用户", description = "删除用户")
    public ResponseEntity<Result<Object>> delete(@PathVariable String id) {
        return null;
    }
}
