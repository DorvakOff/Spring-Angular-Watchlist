package com.dorvak.webapp.metier.repositories;

import com.dorvak.webapp.metier.models.Watchlist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface WatchlistRepository extends CrudRepository<Watchlist, String> {

    Watchlist findByOwnerID(String ownerID);

    @Query("SELECT NVL(AVG(rating), 0) FROM JSONMovie WHERE imdbID = ?1 and rating > 0")
    float getAverageRating(String imdbID);
}
