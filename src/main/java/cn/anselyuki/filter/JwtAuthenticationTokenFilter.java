package cn.anselyuki.filter;

import cn.anselyuki.repository.RedisCache;
import cn.anselyuki.service.model.LoginUser;
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
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
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

/**
 * @author AnselYuki
 */
@SuppressWarnings("ALL")
@Slf4j
@Setter
@Component
@RequiredArgsConstructor
@ConfigurationProperties("jwt")
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final RedisCache redisCache;
    private String secret;
    private String header;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            var token = extractToken(request);
            var jwt = parseAndValidateJwt(token);
            var user = retrieveAndValidateUser(jwt);
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException ex) {
            logger.trace(ex.getMessage());
        } catch (Exception ignored) {
            log.info(ignored.getMessage());
        } finally {
            log.info("[{}] Access Interface [{}]",
                    SecurityContextHolder.getContext().getAuthentication().getName(),
                    request.getRequestURI());
            filterChain.doFilter(request, response);
        }
    }

    private String extractToken(HttpServletRequest request) throws Exception {
        if (SecurityContextHolder.getContext().getAuthentication() != null) throw new Exception("no need to auth");
        String token = request.getHeader(header);
        if (token == null) throw new Exception("token not found");
        return token;
    }

    private JWT parseAndValidateJwt(String token) throws BadCredentialsException {
        if (!JWTUtil.verify(token, secret.getBytes()))
            throw new BadCredentialsException("invalid token");
        var jwt = JWTUtil.parseToken(token);
        var now = DateTime.now();


        NumberWithFormat format = (NumberWithFormat) jwt.getPayload(RegisteredPayload.NOT_BEFORE);
        Long longValue = format.longValue();

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
