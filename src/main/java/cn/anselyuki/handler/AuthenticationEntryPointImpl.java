package cn.anselyuki.handler;

import cn.anselyuki.common.utils.WebUtils;
import cn.anselyuki.controller.response.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author AnselYuki
 */
@Component
@RequiredArgsConstructor
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        Result<Void> result = Result.fail(HttpStatus.UNAUTHORIZED.value(), authException.getMessage());
        String json = objectMapper.writeValueAsString(result);
        WebUtils.renderString(response, json, HttpStatus.UNAUTHORIZED.value());
    }
}