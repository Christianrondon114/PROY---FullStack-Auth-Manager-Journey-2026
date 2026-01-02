package com.attemp3.sc.security;

import com.attemp3.sc.security.filter.DebugExceptionFilter;
import com.attemp3.sc.security.filter.RequestTimingFilter;
import com.attemp3.sc.service.SecurityService;
import jakarta.servlet.DispatcherType;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityService securityService;

    public SecurityConfig(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        RequestCache nullRequestCache = new NullRequestCache();

        requestCache.setMatchingRequestParameterName("continue");


        http
                .addFilterBefore(
                        requestTimingFilter(),
                        SecurityContextHolderFilter.class)

                .csrf(AbstractHttpConfigurer::disable)

                .httpBasic(Customizer.withDefaults())

                .formLogin(form -> form
                        .loginPage("/myLogin.html")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/html/home_admin.html", true)
                        .permitAll()
                )

                .addFilterAt(
                        debugExceptionFilter(),
                        ExceptionTranslationFilter.class
                )

                .authorizeHttpRequests((authorize) -> authorize

                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()

                        .requestMatchers("/html/myLogin.html", "/css/**", "/js/**", "/images/**", "/error","/html/navbar.html").permitAll()

                        .requestMatchers("/html/home_admin.html").hasRole("ADMIN")

                        .requestMatchers("/html/usuarios.html").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/users/**")
                            .access((Authentication, context) ->
                                    new AuthorizationDecision(securityService.canRead(Authentication.get())))

                        .requestMatchers(HttpMethod.POST, "/api/users")
                            .access((Authentication, context) ->
                                new AuthorizationDecision(securityService.canCreate(Authentication.get())))

                        .requestMatchers(HttpMethod.DELETE, "/api/users/{id}")
                            .access((Authentication, context) ->
                                    new AuthorizationDecision(securityService.canDelete(Authentication.get())))

                        .requestMatchers(HttpMethod.PUT, "/api/users/{id}")
                        .access((Authentication, context) ->
                                new AuthorizationDecision(securityService.canUpdate(Authentication.get())))
                )

                .requestCache((cache) -> cache
                        .requestCache(nullRequestCache)
                );

        return http.build();
    }

    @Bean
    public DebugExceptionFilter debugExceptionFilter() {
        return new DebugExceptionFilter();
    }


    @Bean
    public FilterRegistrationBean<DebugExceptionFilter> debugExceptionFilterFilterRegistrationBean(DebugExceptionFilter filter) {
        FilterRegistrationBean<DebugExceptionFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }


    @Bean
    public RequestTimingFilter requestTimingFilter() {
        return new RequestTimingFilter();
    }

    @Bean
    public FilterRegistrationBean<RequestTimingFilter> requestTimingFilterRegistration(RequestTimingFilter filter) {
        FilterRegistrationBean<RequestTimingFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    static RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("ADMIN").implies("MOD")
                .role("MOD").implies("USER")
                .role("USER").implies("GUEST")
                .build();
    }

    @Bean
    static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }

    @Bean
    static GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("ROLE_");
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var user = User.withUsername("admin")
                .password("{noop}123")
                .authorities("ROLE_ADMIN", "delete", "write","read","create")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
