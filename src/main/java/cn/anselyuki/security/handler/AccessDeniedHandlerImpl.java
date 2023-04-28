package cn.anselyuki.security.handler;

import cn.anselyuki.common.utils.SpringUtils;
import cn.anselyuki.common.utils.WebUtils;
import cn.anselyuki.controller.response.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author AnselYuki
 */
@Component
@RequiredArgsConstructor
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        accessDeniedException.printStackTrace();
        Result<Void> result = new Result<>(HttpStatus.UNAUTHORIZED.value(), accessDeniedException.getMessage(), null, SpringUtils.getActiveProfile());
        String json = objectMapper.writeValueAsString(result);
        WebUtils.renderString(response, json, HttpStatus.UNAUTHORIZED.value());
    }
}
