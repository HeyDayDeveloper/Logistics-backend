package cn.anselyuki.security.filter;

import cn.anselyuki.common.utils.RedisCache;
import cn.anselyuki.security.LoginUser;
import cn.hutool.core.convert.NumberWithFormat;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.RegisteredPayload;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
@SuppressWarnings("NullableProblems")
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final RedisCache redisCache;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.header}")
    private String header;
    @Value("${jwt.enabled}")
    private Boolean enabled;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (!enabled) {
            log.warn("JWT认证已禁用,请确定是否为测试环境");
            filterChain.doFilter(request, response);
            return;
        }
        try {
            var token = extractToken(request);
            var jwt = parseAndValidateJwt(token);
            var user = retrieveAndValidateUser(jwt);
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            if (Objects.nonNull(SecurityContextHolder.getContext().getAuthentication()))
                log.info("[{}] Access [{}]",
                        SecurityContextHolder.getContext().getAuthentication().getName(),
                        request.getRequestURI());
            filterChain.doFilter(request, response);
        }
    }

    private String extractToken(HttpServletRequest request) throws Exception {
        if (SecurityContextHolder.getContext().getAuthentication() != null) throw new Exception("no need to auth");
        var head = request.getHeader(header);
        if (head == null || !head.startsWith("Bearer ")) throw new Exception("token not found");
        return head.substring(7);
    }

    private JWT parseAndValidateJwt(String token) throws BadCredentialsException {
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

    private LoginUser retrieveAndValidateUser(JWT jwt) throws AuthenticationException {
        var redisKey = "LOGIN:" + jwt.getPayload("userId");
        var user = redisCache.getCache(redisKey, LoginUser.class);
        if (user == null) throw new UsernameNotFoundException("user not found");
        var jwtToken = jwt.getPayload("token").toString();
        if (!Objects.equals(user.getToken(), jwtToken)) throw new BadCredentialsException("invalid token");
        return user;
    }
}
