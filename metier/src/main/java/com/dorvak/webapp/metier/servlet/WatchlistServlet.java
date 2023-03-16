package com.dorvak.webapp.metier.servlet;

import com.dorvak.webapp.metier.RepositoryAutowire;
import com.dorvak.webapp.metier.models.JSONMovie;
import com.dorvak.webapp.metier.models.Watchlist;
import com.dorvak.webapp.moteur.servicelet.InputData;
import com.dorvak.webapp.moteur.servicelet.OutputData;
import com.dorvak.webapp.moteur.servicelet.WebServlet;

import java.util.ArrayList;
import java.util.Collections;

public class WatchlistServlet extends WebServlet {

    private static Watchlist getWatchlist(InputData inputData) {
        return RepositoryAutowire.getInstance().getWatchlistRepository().findByOwnerID(inputData.getUser().getUserId());
    }

    @Override
    public void toAdd(InputData inputData, OutputData outputData) {
        Watchlist watchlist = getWatchlist(inputData);
        if (watchlist == null) {
            watchlist = new Watchlist(new ArrayList<>(), inputData.getUser().getUserId());
            RepositoryAutowire.getInstance().getWatchlistRepository().save(watchlist);
        }
        JSONMovie movie = inputData.get(JSONMovie.class, "movie");

        watchlist.addMovie(movie);

        RepositoryAutowire.getInstance().getWatchlistRepository().save(watchlist);

        setData("watchlist", watchlist.getWatchlistItems());
        this.sendData(outputData);
    }

    @Override
    public void toInit(InputData inputData, OutputData outputData) {
        Watchlist watchlist = getWatchlist(inputData);

        if (watchlist == null) {
            setData("watchlist", Collections.emptyList());
        } else {
            setData("watchlist", watchlist.getWatchlistItems());
        }

        this.sendData(outputData);
    }

    @Override
    public void toDelete(InputData inputData, OutputData outputData) {
        Watchlist watchlist = getWatchlist(inputData);
        if (watchlist == null) {
            setData("watchlist", Collections.emptyList());
            this.sendData(outputData);
            return;
        }
        String imdbID = inputData.get("imdbID");

        watchlist.removeMovie(imdbID);

        RepositoryAutowire.getInstance().getWatchlistRepository().save(watchlist);

        setData("watchlist", watchlist.getWatchlistItems());
        this.sendData(outputData);
    }
}
