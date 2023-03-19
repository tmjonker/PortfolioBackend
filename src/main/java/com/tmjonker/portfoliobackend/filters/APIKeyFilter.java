package com.tmjonker.portfoliobackend.filters;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class APIKeyFilter implements Filter {

    private String apiKeyHeader;
    private String apiKey;

    public APIKeyFilter(String apiKeyHeader, String apiKey) {
        this.apiKey = apiKey;
        this.apiKeyHeader = apiKeyHeader;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
            String originHeader = ((HttpServletRequest) servletRequest).getHeader("Origin");
            if (originHeader != null && originHeader.contains("angular-portfolio14-dev.us-east-1.elasticbeanstalk.com")) {
                String apiKey = getApiKey((HttpServletRequest) servletRequest);
                if (apiKey != null) {
                    if (apiKey.equals(this.apiKey)) {
                        APIKeyAuthToken apiToken = new APIKeyAuthToken(apiKey, AuthorityUtils.NO_AUTHORITIES);
                        SecurityContextHolder.getContext().setAuthentication(apiToken);
                    } else {
                        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
                        httpResponse.setStatus(401);
                        httpResponse.getWriter().write("Invalid API Key");
                        return;
                    }
                } else {
                    HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
                    httpResponse.setStatus(401);
                    httpResponse.getWriter().write("No API Key Found");
                    return;
                }
            } else {
                HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
                httpResponse.setStatus(401);
                httpResponse.getWriter().write("Wrong request URI");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getApiKey(HttpServletRequest request) {

        String apiKey = null;
        String header = request.getHeader("TMJonker");

        if (header != null) {
            header = header.trim();
            if (header.toLowerCase().startsWith(apiKeyHeader + " ")) {
                apiKey = header.substring(apiKeyHeader.length()).trim();
            }
        }

        return apiKey;
    }
}
