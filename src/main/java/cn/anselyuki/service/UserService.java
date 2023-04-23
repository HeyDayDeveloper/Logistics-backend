package cn.anselyuki.service;

import cn.anselyuki.controller.request.userLoginDTO;
import cn.anselyuki.controller.response.LoginResponse;
import cn.anselyuki.controller.response.Result;

import javax.security.auth.login.LoginException;

/**
 * @author AnselYuki
 * @date 2023/4/23 17:03
 */
public interface UserService {
    /**
     * 用户登录
     *
     * @param user 用户名与密码
     * @return token
     */
    Result<LoginResponse> login(userLoginDTO user) throws LoginException;
}
