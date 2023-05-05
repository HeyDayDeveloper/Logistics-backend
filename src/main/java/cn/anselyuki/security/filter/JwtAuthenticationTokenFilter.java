package cn.anselyuki.security.filter;

import cn.anselyuki.common.utils.AuthUtils;
import cn.anselyuki.common.utils.RedisCache;
import cn.anselyuki.security.LoginUser;
import cn.hutool.jwt.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
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
            log.warn("JWT authentication has been disabled, please disable it only in the testing environment");
            filterChain.doFilter(request, response);
            return;
        }
        try {
            var token = AuthUtils.extractToken(request, header);
            var jwt = AuthUtils.parseAndValidateJwt(token, secret);
            var user = retrieveAndValidateUser(jwt);
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            log.info(e.getMessage());
            return;
        }
        if (Objects.nonNull(SecurityContextHolder.getContext().getAuthentication())) {
            log.info("[{}] Access [{}]",
                    SecurityContextHolder.getContext().getAuthentication().getName(),
                    request.getRequestURI());
        }
        filterChain.doFilter(request, response);
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
