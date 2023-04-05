package com.dorvak.webapp.metier.managers;

import com.dorvak.webapp.metier.AppAutowire;
import com.dorvak.webapp.metier.models.Watchlist;
import com.dorvak.webapp.metier.repositories.WatchlistRepository;
import com.dorvak.webapp.moteur.model.User;

import java.util.ArrayList;

public class MovieManager {

    public static Watchlist getWatchlist(User user) {
        Watchlist watchlist = AppAutowire.getInstance().getRepository(WatchlistRepository.class).findByOwnerID(user.getUserId());
        if (watchlist == null) {
            watchlist = new Watchlist(new ArrayList<>(), user);
            AppAutowire.getInstance().getRepository(WatchlistRepository.class).save(watchlist);
        }
        return watchlist;
    }
}
