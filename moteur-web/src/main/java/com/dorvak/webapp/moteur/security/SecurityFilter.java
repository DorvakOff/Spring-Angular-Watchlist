package com.dorvak.webapp.moteur.security;

import com.dorvak.webapp.moteur.MoteurWebApplication;
import com.dorvak.webapp.moteur.security.bearer.CustomBearerAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Order(1)
@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final List<String> AUTH_BLACKLIST = List.of(
            "/api/servlet/execute"
    );

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if ("OPTIONS".equals(request.getMethod())) {
            res.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, res);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String authToken = authHeader.substring(7);
            CustomBearerAuthentication auth = new CustomBearerAuthentication(authToken);
            CustomBearerAuthentication result = MoteurWebApplication.getInstance().getCustomBearerAuthenticationManager().authenticate(auth);
            if (result.isAuthenticated()) {
                request.setAttribute("user", result.getDetails());
                chain.doFilter(request, res);
            } else {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            }
        } else {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest req) {
        return !AUTH_BLACKLIST.contains(req.getRequestURI());
    }
}
