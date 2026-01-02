package com.attemp3.sc.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class DebugExceptionFilter implements Filter {

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filter) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        System.out.println("DebugExceptionFilter AT THE EXCEPTION POINT: INICIO para " + request.getRequestURI());

        try {
            filter.doFilter(request, servletResponse);
        } catch (Exception ex) {
            System.err.println("DebugExceptionFilter AT THE EXCEPTION POINT: Capturando error -> " + ex.getClass().getSimpleName());
            throw ex;
        }

        System.out.println("DebugExceptionFilter AT THE EXCEPTION POINT: FIN para " + request.getRequestURI());
    }
}
