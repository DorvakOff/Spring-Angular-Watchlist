package com.dorvak.webapp.metier.repositories;

import com.dorvak.webapp.metier.models.TrendingJsonMovie;
import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.List;

public interface TrendingMoviesRepository extends CrudRepository<TrendingJsonMovie, String> {

    List<TrendingJsonMovie> findTop10ByDateAfterOrderByDateDescRankAsc(Instant date);

}
