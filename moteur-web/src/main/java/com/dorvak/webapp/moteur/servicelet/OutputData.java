package com.dorvak.webapp.moteur.servicelet;

import java.util.HashMap;
import java.util.Map;

public class OutputData extends IOData {

    private final Map<String, Object> data;

    public OutputData(String servletName, String action) {
        super(servletName, action);
        this.data = new HashMap<>();
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void sendData(Map<String, Object> data) {
        data.putAll(this.data);
        this.data.clear();
        this.data.putAll(data);
    }

    public void setError(String message) {
        this.data.put("error", message);
    }
}
