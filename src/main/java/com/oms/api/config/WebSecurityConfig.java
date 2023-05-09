package com.oms.api.config;

import com.oms.api.security.JwtAccessDeniedHandler;
import com.oms.api.security.JwtAuthenticationEntryPoint;
import com.oms.api.security.JwtAuthenticationTokenFilter;
import com.oms.api.utils.JwtTokenUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig {

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtTokenUtils jwtTokenUtils;

    public WebSecurityConfig(JwtAccessDeniedHandler jwtAccessDeniedHandler, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtTokenUtils jwtTokenUtils) {

        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtTokenUtils = jwtTokenUtils;

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 禁用 CSRF
                .csrf().disable()

                // 授权异常
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).accessDeniedHandler(jwtAccessDeniedHandler)

                // 防止iframe 造成跨域
                .and().headers().frameOptions().disable()

                // 不创建会话
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and().authorizeRequests()

                // 放行静态资源
                .requestMatchers(HttpMethod.GET, "/*.html", "/*/*.html", "/*/*.css", "/*/*.js", "/webSocket/*").permitAll()

                // 放行OPTIONS请求
                .requestMatchers(HttpMethod.OPTIONS, "/*").permitAll()

                //允许匿名及登录用户访问
                .requestMatchers("/login", "/error/*").permitAll()
                // 所有请求都需要认证
                .anyRequest().authenticated();

        // 禁用缓存
        httpSecurity.headers().cacheControl();

        // 添加JWT filter
        httpSecurity.apply(new TokenConfigurer(jwtTokenUtils));
        return httpSecurity.build();
    }

    public class TokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

        private final JwtTokenUtils jwtTokenUtils;

        public TokenConfigurer(JwtTokenUtils jwtTokenUtils) {

            this.jwtTokenUtils = jwtTokenUtils;
        }

        @Override
        public void configure(HttpSecurity http) {
            JwtAuthenticationTokenFilter customFilter = new JwtAuthenticationTokenFilter(jwtTokenUtils);
            http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
        }
    }

}