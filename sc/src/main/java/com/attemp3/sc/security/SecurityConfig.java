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
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityService securityService;

    public SecurityConfig(SecurityService securityService) {
        this.securityService = securityService;
    }

    // BASIC RESOURCES FILTER CHAIN
    @Bean
    @Order(1)
    public SecurityFilterChain resourcesFilterChain(HttpSecurity http) throws Exception {

        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        RequestCache nullRequestCache = new NullRequestCache();

        return http
                .securityMatcher("/css/**", "/js/**", "/favicon.ico", "/images/**")
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .requestCache(cache -> cache
                        .requestCache(nullRequestCache))
                .build();
    }

    // API SECURITY FILTER CHAIN
    @Bean
    @Order(2)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/**")

                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()

                        .requestMatchers("/api/register").permitAll()

        // ------------------ PRODUCTS API SECURITY -----------------------



                        .requestMatchers(HttpMethod.GET, "/api/products/**")
                        .access((Authentication, context) ->
                                new AuthorizationDecision(securityService.canRead(Authentication.get())))

                        .requestMatchers(HttpMethod.POST, "/api/products")
                        .access((Authentication, context) ->
                                new AuthorizationDecision(securityService.canCreate(Authentication.get())))

                        .requestMatchers(HttpMethod.DELETE, "/api/products/{id}")
                        .access((Authentication, context) ->
                                new AuthorizationDecision(securityService.canDelete(Authentication.get())))

                        .requestMatchers(HttpMethod.PUT, "/api/products/{id}")
                        .access((Authentication, context) ->
                                new AuthorizationDecision(securityService.canUpdate(Authentication.get())))

                                .requestMatchers(HttpMethod.GET, "/api/public/products")
                                .access((Authentication, context) ->
                                        new AuthorizationDecision(securityService.canReadCards(Authentication.get())))





         // ------------------ USERS API SECURITY -----------------------

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
                        .successHandler(myAuthenticationSuccessHandler())
                        .permitAll()
                )
                .authorizeHttpRequests(auth -> auth
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                        .requestMatchers("/html/myLogin.html", "/favicon.ico").permitAll()
                        .requestMatchers("/html/public/navbar_user.html", "/html/administrador/navbar_admin.html").permitAll()
                        .requestMatchers("/html/administrador/**").hasAnyRole("ADMIN","MOD")
                        .requestMatchers("/html/public/**").hasAnyRole("GUEST")
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout.logoutSuccessUrl("/html/myLogin.html"))
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

    // CREATING BEAN PASSWORD ENCODER FOR ENCRYPT USER'S PASSWORDS.
    /*
        Explicación en Español: Esta personalización sirve para si un futuro se migre el
        sistema a uno más grande y/o se quiera cambiar la encryptación por Default, solo
        tengo que cambiar el nombre principal del encrypt para que se registren las nuevas
        contraseñas con la nueva encriptación. No tengo que reiniciar contraseñas ni nada.
     */

    @Bean
    public PasswordEncoder passwordEncoder(){
        String idForEncode = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();

        encoders.put(idForEncode, new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_5());
        encoders.put("pbkdf2@SpringSecurity_v5_8", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("scrypt", SCryptPasswordEncoder.defaultsForSpringSecurity_v4_1());
        encoders.put("scrypt@SpringSecurity_v5_8", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("argon2", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_2());
        encoders.put("argon2@SpringSecurity_v5_8", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("sha256", new StandardPasswordEncoder());

         return new DelegatingPasswordEncoder(idForEncode, encoders);
    }

    // UserDetailsService Bean Instation
    @Bean
    CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService();
    }

    // CUSTON FILTER: custom auth success handler by roles
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            var authorities = authentication.getAuthorities();

            String redirectUrl = "/html/public/home_user.html";

            for (var authority : authorities) {
                if (authority.getAuthority().equals("ROLE_ADMIN") ||
                        authority.getAuthority().equals("ROLE_MOD")) {
                    redirectUrl = "/html/administrador/home_admin.html";
                    break;
                }
            }

            response.sendRedirect(redirectUrl);
        };
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

    -------- USER IN MEMORY CONFIG -------

    // CUSTOM USER DETAILS WITH AUTORITHIES PASSWORD ETC
    @Bean
    public UserDetailsService userDetailsService() {
        var user = User.withUsername("admin")
                .password("{noop}123")
                .authorities("ROLE_ADMIN", "delete", "write","read","create")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
     */
}
