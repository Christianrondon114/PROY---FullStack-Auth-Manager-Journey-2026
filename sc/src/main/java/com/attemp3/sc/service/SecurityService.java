package com.attemp3.sc.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private final RoleHierarchy roleHierarchy;

    public SecurityService(RoleHierarchy roleHierarchy) {
        this.roleHierarchy = roleHierarchy;
    }

    public boolean canRead(Authentication auth){
        var authorities = roleHierarchy.getReachableGrantedAuthorities(auth.getAuthorities());

        return authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
                && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("read"));
    }

    public boolean canCreate(Authentication auth){
        var authorities = roleHierarchy.getReachableGrantedAuthorities(auth.getAuthorities());

        return authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
                && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("create"));
    }

    public boolean canDelete(Authentication auth){
        var authorities = roleHierarchy.getReachableGrantedAuthorities(auth.getAuthorities());

        return authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
                && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("delete"));
    }

    public boolean canUpdate(Authentication auth){
        var authorities = roleHierarchy.getReachableGrantedAuthorities(auth.getAuthorities());

        return authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_MOD"))
                && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("write"));
    }
}
