package cn.anselyuki.security.handler;

import cn.anselyuki.common.annotation.AccessLimit;
import cn.anselyuki.common.utils.IpUtils;
import cn.anselyuki.common.utils.SpringUtils;
import cn.anselyuki.common.utils.WebUtils;
import cn.anselyuki.controller.response.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author AnselYuki
 */
@Component
public class AccessLimitInterceptHandlerImpl implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AccessLimitInterceptHandlerImpl.class);


    /**
     * 接口调用前检查对方ip是否频繁调用接口
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理器
     * @return 是否通过
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            // handler是否为 HandleMethod 实例
            if (handler instanceof HandlerMethod handlerMethod) {
                // 获取方法
                Method method = handlerMethod.getMethod();
                // 判断方式是否有AccessLimit注解，有的才需要做限流
                if (!method.isAnnotationPresent(AccessLimit.class)) {
                    return true;
                }
                StringRedisTemplate stringRedisTemplate = SpringUtils.getApplicationContext()
                        .getBean(StringRedisTemplate.class);

                // 获取注解上的内容
                AccessLimit accessLimit = method.getAnnotation(AccessLimit.class);
                if (accessLimit == null) {
                    return true;
                }
                // 获取方法注解上的请求次数
                int times = accessLimit.times();
                // 获取方法注解上的请求时间
                int second = accessLimit.second();

                // 拼接redis key = IP + Api限流
                String key = IpUtils.getIpAddr(request) + request.getRequestURI();

                // 获取redis的value
                Integer maxTimes = null;

                String value = stringRedisTemplate.opsForValue().get(key);
                if (StringUtils.isNotEmpty(value)) {
                    maxTimes = Integer.valueOf(value);
                }
                if (maxTimes == null) {
                    // 如果redis中没有该ip对应的时间则表示第一次调用，保存key到redis
                    stringRedisTemplate.opsForValue().set(key, "1", second, TimeUnit.SECONDS);
                } else if (maxTimes < times) {
                    // 如果redis中的时间比注解上的时间小则表示可以允许访问,这是修改redis的value时间
                    stringRedisTemplate.opsForValue().set(key, String.valueOf(maxTimes + 1), second, TimeUnit.SECONDS);
                } else {
                    // 请求过于频繁
                    logger.info(key + " 请求过于频繁");
                    Result<Void> result = new Result<>(HttpStatus.TOO_MANY_REQUESTS.value(), "请求过于频繁", null);
                    String json = new ObjectMapper().writeValueAsString(result);
                    WebUtils.renderString(response, json, HttpStatus.TOO_MANY_REQUESTS.value());
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error("API请求限流拦截异常，异常原因：", e);
            // throw new Exception("");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}
