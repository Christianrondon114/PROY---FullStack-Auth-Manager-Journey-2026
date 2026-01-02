package com.attemp3.sc.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;


public class RequestTimingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        long startTime = System.currentTimeMillis();
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();
        System.out.println("INICIO: Procesando [" + servletRequest.getContentType() + "] para: " + requestURI);

        filterChain.doFilter(servletRequest,servletResponse);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("FIN: Solicitud completada en " + duration + "ms para: " + requestURI);


    }
}
