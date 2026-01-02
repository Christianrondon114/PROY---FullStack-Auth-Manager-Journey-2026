package com.attemp3.sc.security;

import com.attemp3.sc.security.filter.DebugExceptionFilter;
import com.attemp3.sc.security.filter.RequestTimingFilter;
import com.attemp3.sc.service.SecurityService;
import jakarta.servlet.DispatcherType;
import org.hibernate.cache.internal.DisabledCaching;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
    @Order(1)
    public SecurityFilterChain resourcesFilterChain(HttpSecurity http) throws Exception {

        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        RequestCache nullRequestCache = new NullRequestCache();

        return http
                .securityMatcher("/css/**", "/js/**","html/navbar.html")
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .requestCache(cache -> cache
                        .requestCache(nullRequestCache))
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
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
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    // WEB SECURITY FILTER CHAIN
    @Bean
    @Order(3)
    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(form -> form
                        .loginPage("/html/myLogin.html")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/html/administrador/home_admin.html", true)
                        .permitAll()
                )
                .authorizeHttpRequests(auth -> auth
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                        .requestMatchers("/html/myLogin.html", "/favicon.ico").permitAll()
                        .requestMatchers("/html/administrador/home_admin.html","/html/administrador/usuarios.html").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                .build();
    }

    // CUSTOM FILTER: DebugExceptionFilter.java

    @Bean
    public DebugExceptionFilter debugExceptionFilter() {
        return new DebugExceptionFilter();
    }

    // REGISTER BEAN IN SPRING CONTAINER
    @Bean
    public FilterRegistrationBean<DebugExceptionFilter> debugExceptionFilterFilterRegistrationBean(DebugExceptionFilter filter) {
        FilterRegistrationBean<DebugExceptionFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }

    // CUSTOM FILTER: RequestTimingFilter.java
    @Bean
    public RequestTimingFilter requestTimingFilter() {
        return new RequestTimingFilter();
    }


    // REGISTER BEAN IN SPRING CONTAINER
    @Bean
    public FilterRegistrationBean<RequestTimingFilter> requestTimingFilterRegistration(RequestTimingFilter filter) {
        FilterRegistrationBean<RequestTimingFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }

    // REGISTER Role Hierarchy -> ADMIN > MOD > USER > GUEST
    @Bean
    static RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("ADMIN").implies("MOD")
                .role("MOD").implies("USER")
                .role("USER").implies("GUEST")
                .build();
    }

    //
    @Bean
    static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }

    // CUSTOM ROLE HIERARCHY PREFIX
    @Bean
    static GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("ROLE_");
    }

    // CUSTOM USER DETAILS WITH AUTORITHIES PASSWORD ETC
    @Bean
    public UserDetailsService userDetailsService() {
        var user = User.withUsername("admin")
                .password("{noop}123")
                .authorities("ROLE_ADMIN", "delete", "write","read","create")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    /* -------  FIRST SECURITY FILTER CHAIN -------
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

     */

}
