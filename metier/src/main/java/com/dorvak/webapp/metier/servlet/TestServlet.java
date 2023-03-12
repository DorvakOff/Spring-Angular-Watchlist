package com.dorvak.webapp.metier.servlet;

import com.dorvak.webapp.moteur.model.User;
import com.dorvak.webapp.moteur.servicelet.InputData;
import com.dorvak.webapp.moteur.servicelet.OutputData;
import com.dorvak.webapp.moteur.servicelet.WebServlet;

public class TestServlet extends WebServlet {

    @Override
    public void toInit(InputData inputData, OutputData outputData) {
        User user = inputData.getUser();
        setData("test", "test");
        setData("user", user.getName());
        sendData(outputData);
    }
}
