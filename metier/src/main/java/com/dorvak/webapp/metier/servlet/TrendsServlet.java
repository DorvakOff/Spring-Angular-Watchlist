package com.dorvak.webapp.metier.servlet;

import com.dorvak.webapp.metier.AppAutowire;
import com.dorvak.webapp.metier.models.TrendingJsonMovie;
import com.dorvak.webapp.metier.repositories.TrendingMoviesRepository;
import com.dorvak.webapp.metier.tasks.CalculateTrendsTask;
import com.dorvak.webapp.moteur.servicelet.InputData;
import com.dorvak.webapp.moteur.servicelet.NoAuth;
import com.dorvak.webapp.moteur.servicelet.OutputData;
import com.dorvak.webapp.moteur.servicelet.WebServlet;

import java.time.Instant;
import java.util.List;

@NoAuth
public class TrendsServlet extends WebServlet {

    @Override
    public void toInit(InputData inputData, OutputData outputData) {
        TrendingMoviesRepository trendingMoviesRepository = AppAutowire.getInstance().getRepository(TrendingMoviesRepository.class);

        Instant todayMidnight = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.DAYS);
        List<TrendingJsonMovie> movies = trendingMoviesRepository.findTop10ByDateAfterOrderByDateDescRankAsc(todayMidnight);

        if (movies.isEmpty()) {
            AppAutowire.getInstance().getComponent(CalculateTrendsTask.class).run();
            movies = trendingMoviesRepository.findTop10ByDateAfterOrderByDateDescRankAsc(todayMidnight);
        }

        setData("popular", movies);

        sendData(outputData);
    }
}
