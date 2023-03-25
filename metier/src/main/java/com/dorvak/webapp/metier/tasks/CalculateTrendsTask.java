package com.dorvak.webapp.metier.tasks;

import com.dorvak.webapp.metier.models.TrendingJsonMovie;
import com.dorvak.webapp.metier.repositories.TrendingMoviesRepository;
import com.dorvak.webapp.metier.services.TrendsService;
import com.dorvak.webapp.moteur.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CalculateTrendsTask implements Runnable {

    @Autowired
    private TrendsService trendsService;

    @Autowired
    private TrendingMoviesRepository trendingMoviesRepository;

    @Scheduled(cron = "0 0 0 * * *", zone = "Europe/Paris")
    @Override
    public void run() {
        AtomicInteger counter = new AtomicInteger(1);
        List<TrendingJsonMovie> movies = trendsService.loadPopularMovies().stream()
                .map(jsonMovie -> new TrendingJsonMovie(counter.getAndIncrement(), jsonMovie))
                .toList();

        trendingMoviesRepository.saveAll(movies);

        LoggerUtils.info("Trends calculated successfully, %d movies saved: %s", movies.size(), movies.stream().map(TrendingJsonMovie::getTitle).toList());
    }
}
