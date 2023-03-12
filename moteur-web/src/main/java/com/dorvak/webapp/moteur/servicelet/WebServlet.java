package com.dorvak.webapp.moteur.servicelet;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class WebServlet {

    private final Map<String, Object> data;

    public WebServlet() {
        this.data = new HashMap<>();
    }

    public void toInit(InputData inputData, OutputData outputData) {
        this.sendData(outputData);
    }

    public void toSave(InputData inputData, OutputData outputData) {
        this.sendData(outputData);
    }

    public void toDelete(InputData inputData, OutputData outputData) {
        this.sendData(outputData);
    }

    protected final void setData(String key, Object value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        data.put(key, value);
    }

    protected final void sendData(OutputData outputData) {
        outputData.sendData(data);
    }
}
