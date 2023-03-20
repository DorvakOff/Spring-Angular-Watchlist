package com.dorvak.webapp.metier.services;

import com.dorvak.webapp.metier.models.JSONMovie;
import com.dorvak.webapp.metier.models.Watchlist;
import com.dorvak.webapp.metier.repositories.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TrendsService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    public List<JSONMovie> getTopMovies() {
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
}
