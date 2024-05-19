package com.sangeng.config;

import com.sangeng.filter.JwtAuthenticationTokenFilter;
import com.sangeng.handler.security.AccessDeniedHandlerImpl;
import com.sangeng.handler.security.AuthenticationEntryPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security配置类
 */
@Configuration
@EnableWebSecurity//开启WebSecurity模式 代替了 implements WebSecurityConfigurerAdapter
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启方法权限注解


public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationConfiguration configuration;
    @Autowired
    AccessDeniedHandlerImpl accessDeniedHandler;
    @Autowired
    AuthenticationEntryPointImpl authenticationEntryPoint;
    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    // 将认证管理器添加到ioc容器
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return configuration.getAuthenticationManager();
    }
    // 密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                 //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/user/login").anonymous()
//                // 注销接口需要认证访问
//                .antMatchers("/logout").authenticated()
//                //个人信息接口必须登录后才能访问
//                .antMatchers("/user/userInfo").authenticated()
                 // 除上面外的所有请求全部不需要认证即可访问
                .anyRequest().permitAll();
        http.exceptionHandling()
                // 认证失败处理类
                .authenticationEntryPoint(authenticationEntryPoint)
                // 权限拒绝处理类
                .accessDeniedHandler(accessDeniedHandler);
        http.logout().disable();
        //允许跨域
        http.cors();
        // 将jwt过滤器添加到UsernamePasswordAuthenticationFilter之前
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }


}
