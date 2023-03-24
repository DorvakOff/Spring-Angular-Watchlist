package com.dorvak.webapp.moteur.security;

import com.dorvak.webapp.moteur.MoteurWebApplication;
import com.dorvak.webapp.moteur.security.bearer.CustomBearerAuthentication;
import com.dorvak.webapp.moteur.service.ServletExecutorService;
import com.dorvak.webapp.moteur.utils.CharacterUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Order(1)
@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final List<String> AUTH_BLACKLIST = List.of(
            "/api/servlet/execute"
    );
    @Autowired
    private ServletExecutorService servletExecutorService;

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
        return !isAuthBlacklisted(req.getRequestURI()) || (isServletRequest(req) && !servletNeedAuth(req));
    }

    private boolean isAuthBlacklisted(String uri) {
        return AUTH_BLACKLIST.contains(uri);
    }

    private boolean servletNeedAuth(HttpServletRequest req) {
        Map<String, Object> body = null;
        try {
            JsonMapper mapper = new JsonMapper();
            mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            mapper.disable(SerializationFeature.CLOSE_CLOSEABLE);
            JavaType type = mapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
            if (req.getInputStream().available() != 0) {
                InputStream inputStream = new ByteArrayInputStream(req.getInputStream().readAllBytes());
                body = mapper.readValue(inputStream, type);
            }
            if (body == null) {
                body = new HashMap<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }

        String servletName = Objects.toString(body.get("servletName"), "");
        String action = Objects.toString(body.get("action"), "");

        if (CharacterUtils.isEmptyTrim(servletName) || CharacterUtils.isEmptyTrim(action)) {
            return true;
        }

        return servletExecutorService.servletNeedAuth(servletName, action);
    }

    private boolean isServletRequest(HttpServletRequest req) {
        return req.getRequestURI().startsWith("/api/servlet/execute") && "POST".equalsIgnoreCase(req.getMethod());
    }
}
