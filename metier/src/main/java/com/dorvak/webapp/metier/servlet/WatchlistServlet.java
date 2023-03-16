package com.dorvak.webapp.metier.servlet;

import com.dorvak.webapp.moteur.servicelet.InputData;
import com.dorvak.webapp.moteur.servicelet.OutputData;
import com.dorvak.webapp.moteur.servicelet.WebServlet;

public class WatchlistServlet extends WebServlet {

    @Override
    public void toAdd(InputData inputData, OutputData outputData) {
        // TODO Auto-generated method stub

        this.sendData(outputData);
    }
}
