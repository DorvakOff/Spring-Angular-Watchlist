package com.dorvak.webapp.metier.repositories;

import com.dorvak.webapp.metier.models.TrendingJsonMovie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrendingMoviesRepository extends CrudRepository<TrendingJsonMovie, String> {

    @Query("SELECT m FROM TrendingJsonMovie m ORDER BY m.date desc LIMIT 10")
    List<TrendingJsonMovie> findLatestTop10();

}
