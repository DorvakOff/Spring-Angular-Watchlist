package com.dorvak.webapp.moteur.servicelet;

import com.dorvak.webapp.moteur.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class InputData extends IOData {

    private final HttpServletRequest request;
    private final User user;

    public InputData(String servletName, String action, HttpServletRequest request) {
        super(servletName, action);
        this.request = request;
        this.user = (User) request.getAttribute("user");
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

}
