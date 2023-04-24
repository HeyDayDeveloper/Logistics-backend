package cn.anselyuki.service;

import cn.anselyuki.controller.request.UserLoginDTO;
import cn.anselyuki.controller.response.LoginResponse;
import cn.anselyuki.controller.response.Result;
import org.springframework.http.ResponseEntity;

import javax.security.auth.login.LoginException;

/**
 * @author AnselYuki
 */
public interface UserService {
    /**
     * 用户登录
     *
     * @param user 用户名与密码
     * @return token
     */
    ResponseEntity<Result<LoginResponse>> login(UserLoginDTO user) throws LoginException;
}
