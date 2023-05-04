package cn.anselyuki.common.utils;

import cn.hutool.core.convert.NumberWithFormat;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.RegisteredPayload;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;

public class AuthUtils {
    public static String extractToken(HttpServletRequest request, String header) throws Exception {
        var head = request.getHeader(header);
        if (head == null || !head.startsWith("Bearer ")) throw new Exception("token not found");
        return head.substring(7);
    }

    public static JWT parseAndValidateJwt(String token, String secret) throws BadCredentialsException {
        if (!JWTUtil.verify(token, secret.getBytes()))
            throw new BadCredentialsException("invalid token");
        var jwt = JWTUtil.parseToken(token);
        var now = DateTime.now();

        NumberWithFormat format = (NumberWithFormat) jwt.getPayload(RegisteredPayload.NOT_BEFORE);
        long longValue = format.longValue();

        var notBefore = DateTime.of(longValue);
        if (now.isBefore(notBefore)) throw new CredentialsExpiredException("haven't come into effect");

        format = (NumberWithFormat) jwt.getPayload(RegisteredPayload.EXPIRES_AT);
        longValue = format.longValue();

        var expiresAt = DateTime.of(longValue);
        if (now.isAfter(expiresAt)) throw new CredentialsExpiredException("token expired");
        return jwt;
    }
}
