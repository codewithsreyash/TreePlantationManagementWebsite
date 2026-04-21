package com.plantree.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuthFilter implements Filter {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("[AuthFilter] Initialized in com.plantree.filter.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Gap 2 Requirement: Log each incoming request (URL + timestamp)
        System.out.println("[AUTH FILTER LOG] " + sdf.format(new Date()) + " - Accessed: " + req.getRequestURI());

        // Gap 2 Requirement: Check if user is authenticated (via Spring Security Context)
        HttpSession session = req.getSession(false);
        boolean isAuthenticated = false;
        if (session != null && session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            isAuthenticated = true;
        }

        // Gap 2 Requirement: Redirect unauthenticated users
        if (!isAuthenticated) {
            System.out.println("[AUTH FILTER LOG] Blocked unauthenticated access to " + req.getRequestURI());
            res.sendRedirect(req.getContextPath() + "/login.xhtml");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup if needed
    }
}
