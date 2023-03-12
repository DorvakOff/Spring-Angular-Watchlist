package com.dorvak.webapp.moteur.dto;

import java.util.Map;

public record ServletDto(String servletName, String action, Map<String, Object> data) {

    public ServletDto(String servletName, String action) {
        this(servletName, action, Map.of());
    }
}
