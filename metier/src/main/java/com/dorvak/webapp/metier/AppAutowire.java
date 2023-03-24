package com.dorvak.webapp.metier;

import com.dorvak.webapp.metier.models.Watchlist;
import com.dorvak.webapp.metier.repositories.WatchlistRepository;
import com.dorvak.webapp.moteur.MoteurWebApplication;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Component;

@Component
public class AppAutowire {

    private static AppAutowire instance;

    private AppAutowire() {
        instance = this;
    }

    public static AppAutowire getInstance() {
        return instance == null ? new AppAutowire() : instance;
    }

    public WatchlistRepository getWatchlistRepository() {
        Repositories repositories = new Repositories(MoteurWebApplication.getInstance().getApplicationContext());
        return (WatchlistRepository) repositories.getRepositoryFor(Watchlist.class).orElseThrow();
    }

    public <S> S getService(Class<S> clazz) {
        return MoteurWebApplication.getInstance().getApplicationContext().getBean(clazz);
    }
}
