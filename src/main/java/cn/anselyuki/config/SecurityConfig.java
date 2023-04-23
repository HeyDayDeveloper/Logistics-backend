package cn.anselyuki.config;

import cn.anselyuki.filter.JwtAuthenticationTokenFilter;
import cn.anselyuki.handler.AuthenticationEntryPointImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author AnselYuki
 */
@SpringBootConfiguration
@RequiredArgsConstructor
public class SecurityConfig {
    /**
     * 允许匿名访问，禁止已登录用户访问
     */
    private static final String[] URL_WHITELIST = {
            "/user/login",
            "/user/register",
    };
    /**
     * 允许任何人访问
     */
    private static final String[] URL_PERMIT_ALL = {
            "/",
            "/version",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-ui/**",
    };
    //用户接口，权限等级1
    private static final String[] URL_AUTHENTICATION_1 = {
            "/user/logout",
    };
    //管理员接口，权限等级2
    private static final String[] URL_AUTHENTICATION_2 = {};
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    private final AuthenticationEntryPointImpl authenticationEntryPoint;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //关闭CSRF,设置无状态连接
        http.csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //允许匿名访问的接口，如果是测试想要方便点就把这段全注释掉
        http.authorizeHttpRequests(authorize ->
                authorize.requestMatchers(URL_WHITELIST).anonymous()
                        .requestMatchers(URL_PERMIT_ALL).permitAll()
                        //权限 0 未激活 1 激活  等等.. (拥有权限1必然拥有权限0 拥有权限2必然拥有权限1、0)
                        //指定接口需要指定权限才能访问 如果不开启RBAC注释掉这一段即可
                        .requestMatchers(URL_AUTHENTICATION_1).hasAuthority("1")
                        //此处用于管理员操作接口
                        .requestMatchers(URL_AUTHENTICATION_2).hasAuthority("2")
                        .anyRequest().authenticated());

        //添加过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //配置异常处理器，处理认证失败的JSON响应
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

        //开启跨域请求
        http.cors();
        return http.build();
    }
}
