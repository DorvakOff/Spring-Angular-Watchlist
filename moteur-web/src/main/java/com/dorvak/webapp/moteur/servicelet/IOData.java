package com.dorvak.webapp.moteur.servicelet;

public class IOData {

    private final String servletName;
    private final String action;

    public IOData(String servletName, String action) {
        this.servletName = servletName;
        this.action = action;
    }

    public final String getServletName() {
        return servletName;
    }

    public final String getAction() {
        return action;
    }
}
