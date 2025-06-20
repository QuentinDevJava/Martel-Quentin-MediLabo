package com.medilabo.ui.config;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthSessionFilter implements Filter {

    private static final List<String> PUBLIC_PATHS = List.of("/auth/login", "/auth/token");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	    throws IOException, ServletException {

	HttpServletRequest req = (HttpServletRequest) request;
	HttpServletResponse res = (HttpServletResponse) response;

	String path = req.getRequestURI();

	if (isPublicPath(path)) {
	    chain.doFilter(request, response);
	    return;
	}

	Object token = req.getSession().getAttribute("token");

	if (token == null) {
	    res.sendRedirect("/auth/login");
	    return;
	}

	chain.doFilter(request, response);
    }

    private boolean isPublicPath(String path) {
	return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }
}