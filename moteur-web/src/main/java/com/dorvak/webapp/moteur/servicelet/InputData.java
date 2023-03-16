package com.dorvak.webapp.moteur.servicelet;

import com.dorvak.webapp.moteur.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

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
        return clazz.cast(data.get(key));
    }

}
