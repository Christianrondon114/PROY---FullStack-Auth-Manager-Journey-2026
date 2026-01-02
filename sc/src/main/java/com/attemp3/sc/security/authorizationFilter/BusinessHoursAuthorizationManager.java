package com.attemp3.sc.security.authorizationFilter;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@Component
public class BusinessHoursAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext>{

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context){
        int hour = LocalDateTime.now().getHour();

        boolean isBusinessHours = hour >= 9 && hour < 17;

        return new AuthorizationDecision(isBusinessHours);
    }

}
