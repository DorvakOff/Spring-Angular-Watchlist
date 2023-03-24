package com.dorvak.webapp.moteur.servicelet;

import com.dorvak.webapp.moteur.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Map;
import java.util.Objects;

public class InputData extends IOData {

    private final HttpServletRequest request;
    private final User user;
    private final Map<String, Object> data;

    public InputData(String servletName, String action, HttpServletRequest request, Map<String, Object> data) {
        super(servletName, action);
        this.request = request;
        this.user = (User) request.getAttribute("user");
        this.data = data;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpSession getSession(boolean create) {
        return request.getSession(create);
    }

    public User getUser() {
        return user;
    }

    public <T> T get(Class<T> clazz, String key) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(data.get(key), clazz);
    }

    public String get(String key) {
        return Objects.toString(data.get(key), "");
    }

    public boolean has(String key) {
        return data.containsKey(key);
    }

}
