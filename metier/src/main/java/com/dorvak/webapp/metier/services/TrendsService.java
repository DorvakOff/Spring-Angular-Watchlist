package com.dorvak.webapp.metier.services;

import com.dorvak.webapp.metier.AppAutowire;
import com.dorvak.webapp.metier.models.JSONMovie;
import com.dorvak.webapp.metier.models.TrendingJsonMovie;
import com.dorvak.webapp.metier.models.Watchlist;
import com.dorvak.webapp.metier.repositories.TrendingMoviesRepository;
import com.dorvak.webapp.metier.repositories.WatchlistRepository;
import com.dorvak.webapp.metier.tasks.CalculateTrendsTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TrendsService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private TrendingMoviesRepository trendingMoviesRepository;

    public List<JSONMovie> loadPopularMovies() {
        List<JSONMovie> topMovies = StreamSupport.stream(watchlistRepository.findAll().spliterator(), false)
                .map(Watchlist::getWatchlistItems)
                .flatMap(Collection::stream).toList();

        Map<JSONMovie, Long> movieCount = topMovies.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        topMovies = movieCount.entrySet().stream().limit(10)
                .sorted(Map.Entry.<JSONMovie, Long>comparingByValue().reversed())
                .map(Map.Entry::getKey).collect(Collectors.toList());

        return topMovies;
    }

    public List<TrendingJsonMovie> getPopularMovies() {
        Instant todayMidnight = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.DAYS).minusSeconds(60);
        List<TrendingJsonMovie> movies = trendingMoviesRepository.findTop10ByDateAfterOrderByDateDescRankAsc(todayMidnight);

        if (movies.isEmpty()) {
            AppAutowire.getInstance().getComponent(CalculateTrendsTask.class).run();
            movies = trendingMoviesRepository.findTop10ByDateAfterOrderByDateDescRankAsc(todayMidnight);
        }

        return movies;
    }
}
