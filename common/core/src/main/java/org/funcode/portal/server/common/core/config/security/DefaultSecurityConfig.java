/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.funcode.portal.server.common.core.config.ApplicationConfig;
import org.funcode.portal.server.common.core.security.filter.JwtTokenFilter;
import org.funcode.portal.server.common.core.security.filter.WechatAuthenticationFilter;
import org.funcode.portal.server.common.core.security.handler.CustomAuthenticationFailureHandler;
import org.funcode.portal.server.common.core.security.handler.CustomAuthenticationSuccessHandler;
import org.funcode.portal.server.common.core.security.handler.WechatAuthenticationFailureHandler;
import org.funcode.portal.server.common.core.security.handler.WechatAuthenticationSuccessHandler;
import org.funcode.portal.server.common.core.security.provider.WeChatAuthenticationProvider;
import org.funcode.portal.server.common.core.security.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.funcode.portal.server.common.core.constant.SecurityConstant.TOKEN_HEADER_KEY;
import static org.funcode.portal.server.common.core.security.filter.WechatAuthenticationFilter.WECHAT_LOGIN_PATH;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Slf4j
@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class DefaultSecurityConfig {

    private final UserDetailsServiceImpl userDetailService;
    private final JwtTokenFilter jwtTokenFilter;
    private final ApplicationConfig applicationConfig;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   WeChatAuthenticationProvider weChatAuthenticationProvider,
                                                   WechatAuthenticationFilter weChatAuthenticationFilter) throws Exception {
        String defaultLoginPage = StringUtils.isBlank(applicationConfig.getSecurity().loginPage()) ? "/login" : applicationConfig.getSecurity().loginPage();
        // HTTP 的 Clear-Site-Data 标头是浏览器支持的指令，用于清除属于拥有网站的 Cookie、存储和缓存
        HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.ALL));
        http
                .cors(cors -> cors
                        .configurationSource(corsWebsiteConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/swagger-ui",
                                "/swagger-ui/**",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/webjars/**",
                                "favicon.ico",
                                "/doc.html",
                                defaultLoginPage
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, WECHAT_LOGIN_PATH).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .requestMatchers(antMatcher("/**/anonymous")).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .formLogin(login -> login
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .loginPage(defaultLoginPage)
                        .loginProcessingUrl("/api/v1/auth/login")
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureHandler(customAuthenticationFailureHandler))
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(clearSiteData)
                        .logoutSuccessUrl(
                                StringUtils.isBlank(applicationConfig.getSecurity().logoutSuccessUrl()) ? "/login?logout" : applicationConfig.getSecurity().logoutSuccessUrl())
                )
                .authenticationProvider(weChatAuthenticationProvider)
                .authenticationProvider(daoAuthenticationProvider())
                .addFilterBefore(
                        jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(
                        weChatAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public WechatAuthenticationFilter weChatAuthenticationFilter(AuthenticationManager authenticationManager,
                                                                 WechatAuthenticationSuccessHandler wechatAuthenticationSuccessHandler,
                                                                 WechatAuthenticationFailureHandler wechatAuthenticationFailureHandler) {
        WechatAuthenticationFilter filter = new WechatAuthenticationFilter(authenticationManager, wechatAuthenticationSuccessHandler, wechatAuthenticationFailureHandler);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private CorsConfigurationSource corsWebsiteConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(applicationConfig.getSecurity().corsAllowedOriginPatterns());
        configuration.setAllowedMethods(applicationConfig.getSecurity().corsAllowedMethods());
        configuration.addAllowedHeader(TOKEN_HEADER_KEY);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
