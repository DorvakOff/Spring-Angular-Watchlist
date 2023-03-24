package com.dorvak.webapp.metier.servlet;

import com.dorvak.webapp.metier.AppAutowire;
import com.dorvak.webapp.metier.services.TrendsService;
import com.dorvak.webapp.moteur.servicelet.InputData;
import com.dorvak.webapp.moteur.servicelet.NoAuth;
import com.dorvak.webapp.moteur.servicelet.OutputData;
import com.dorvak.webapp.moteur.servicelet.WebServlet;

@NoAuth
public class TrendsServlet extends WebServlet {
    
    @Override
    public void toInit(InputData inputData, OutputData outputData) {
        TrendsService trendsService = AppAutowire.getInstance().getService(TrendsService.class);

        setData("popular", trendsService.getTopMovies());

        sendData(outputData);
    }
}
