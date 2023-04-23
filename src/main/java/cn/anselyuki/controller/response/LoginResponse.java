package cn.anselyuki.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String validBefore;
    private String validAfter;
    private String refreshToken;
    private String refreshTokenValidBefore;
    private UserInfoVO userInfo;
}
