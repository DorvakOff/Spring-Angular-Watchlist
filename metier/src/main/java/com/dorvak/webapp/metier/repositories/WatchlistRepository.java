package com.dorvak.webapp.metier.repositories;

import com.dorvak.webapp.metier.models.Watchlist;
import org.springframework.data.repository.CrudRepository;

public interface WatchlistRepository extends CrudRepository<Watchlist, String> {

    Watchlist findByOwnerID(String ownerID);
}
