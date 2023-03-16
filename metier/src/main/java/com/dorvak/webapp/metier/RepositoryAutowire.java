package com.dorvak.webapp.metier;

import com.dorvak.webapp.metier.models.Watchlist;
import com.dorvak.webapp.metier.repositories.WatchlistRepository;
import com.dorvak.webapp.moteur.MoteurWebApplication;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Component;

@Component
public class RepositoryAutowire {

    private static RepositoryAutowire instance;

    private RepositoryAutowire() {
        instance = this;
    }

    public static RepositoryAutowire getInstance() {
        return instance == null ? new RepositoryAutowire() : instance;
    }

    public WatchlistRepository getWatchlistRepository() {
        Repositories repositories = new Repositories(MoteurWebApplication.getInstance().getApplicationContext());
        return (WatchlistRepository) repositories.getRepositoryFor(Watchlist.class).orElseThrow();
    }
}
