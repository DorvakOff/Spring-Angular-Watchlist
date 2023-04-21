package com.dorvak.webapp.metier.servlet;

import com.dorvak.webapp.metier.AppAutowire;
import com.dorvak.webapp.metier.managers.MovieManager;
import com.dorvak.webapp.metier.models.JSONMovie;
import com.dorvak.webapp.metier.models.Watchlist;
import com.dorvak.webapp.metier.repositories.WatchlistRepository;
import com.dorvak.webapp.moteur.servicelet.InputData;
import com.dorvak.webapp.moteur.servicelet.NoAuth;
import com.dorvak.webapp.moteur.servicelet.OutputData;
import com.dorvak.webapp.moteur.servicelet.WebServlet;

import java.util.Optional;

public class WatchlistServlet extends WebServlet {

    @Override
    public void toAdd(InputData inputData, OutputData outputData) {
        Watchlist watchlist = MovieManager.getWatchlist(inputData.getUser());

        JSONMovie movie = inputData.get(JSONMovie.class, "movie");

        watchlist.addMovie(movie);

        AppAutowire.getInstance().getRepository(WatchlistRepository.class).save(watchlist);

        setData("watchlist", watchlist);
        this.sendData(outputData);
    }

    @Override
    public void toInit(InputData inputData, OutputData outputData) {
        Watchlist watchlist = MovieManager.getWatchlist(inputData.getUser());

        setData("watchlist", watchlist);

        this.sendData(outputData);
    }

    @Override
    public void toDelete(InputData inputData, OutputData outputData) {
        Watchlist watchlist = MovieManager.getWatchlist(inputData.getUser());

        String imdbID = inputData.get("imdbID");

        watchlist.removeMovie(imdbID);

        AppAutowire.getInstance().getRepository(WatchlistRepository.class).save(watchlist);

        setData("watchlist", watchlist);
        this.sendData(outputData);
    }

    @Override
    public void toEdit(InputData inputData, OutputData outputData) {
        Watchlist watchlist = MovieManager.getWatchlist(inputData.getUser());

        if (inputData.has("description")) {
            String description = inputData.get("description");
            watchlist.setDescription(description);
        }

        if (inputData.has("publicList")) {
            watchlist.setPublicList(inputData.get(Boolean.class, "publicList"));
        }

        if (inputData.has("description") || inputData.has("publicList")) {
            AppAutowire.getInstance().getRepository(WatchlistRepository.class).save(watchlist);
        }

        this.sendData(outputData);
    }

    @NoAuth
    public void toLoadById(InputData inputData, OutputData outputData) {
        Optional<Watchlist> watchlist = AppAutowire.getInstance().getRepository(WatchlistRepository.class).findById(inputData.get("id"));

        if (watchlist.isPresent() && watchlist.get().isPublicList()) {
            setData("watchlist", watchlist.get());
            this.sendData(outputData);
        } else {
            outputData.setError("No watchlist found with this id");
        }

        this.sendData(outputData);
    }
}
