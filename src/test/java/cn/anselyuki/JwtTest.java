package cn.anselyuki;

import cn.hutool.core.convert.NumberWithFormat;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.RegisteredPayload;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;

/**
 * @author AnselYuki
 */
public class JwtTest {
    @Test
    public void testJwtToken() throws BadCredentialsException {
        //noinspection SpellCheckingInspection
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYmYiOjE2ODIzMDgzMTgzOTQsImV4cCI6MTY4MjMyOTkxODM5NCwiaWF0IjoxNjgyMzA4MzE4Mzk0LCJ1c2VySWQiOiIwOTBmZGRkMy0xZmI1LTRlNGQtODRhZi0yOTM4MmJlZmM2YmUiLCJ0b2tlbiI6IlFsVGlLdGxKNTYyWmVBUWcifQ.aIneRSJUZokO7El5vIBeBUmsafELn2CdtmYLgqkN_y8";
        String secret = "if you want to use jwt, you should set a secret";
        if (!JWTUtil.verify(token, secret.getBytes()))
            throw new BadCredentialsException("invalid token");
        var jwt = JWTUtil.parseToken(token);
        var now = DateTime.now();
        NumberWithFormat format = (NumberWithFormat) jwt.getPayload(RegisteredPayload.NOT_BEFORE);
        Long longValue = format.longValue();
        System.out.println(longValue);
        var notBefore = DateTime.of(longValue);
        System.out.println("notBefore = " + notBefore);
    }
}
