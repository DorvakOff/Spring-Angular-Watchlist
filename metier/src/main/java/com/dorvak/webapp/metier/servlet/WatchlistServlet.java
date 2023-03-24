package com.dorvak.webapp.metier.servlet;

import com.dorvak.webapp.metier.AppAutowire;
import com.dorvak.webapp.metier.models.JSONMovie;
import com.dorvak.webapp.metier.models.Watchlist;
import com.dorvak.webapp.metier.repositories.WatchlistRepository;
import com.dorvak.webapp.moteur.servicelet.InputData;
import com.dorvak.webapp.moteur.servicelet.OutputData;
import com.dorvak.webapp.moteur.servicelet.WebServlet;

import java.util.ArrayList;

public class WatchlistServlet extends WebServlet {

    private static Watchlist getWatchlist(InputData inputData) {
        Watchlist watchlist = AppAutowire.getInstance().getRepository(WatchlistRepository.class).findByOwnerID(inputData.getUser().getUserId());
        if (watchlist == null) {
            watchlist = new Watchlist(new ArrayList<>(), inputData.getUser().getUserId());
            AppAutowire.getInstance().getRepository(WatchlistRepository.class).save(watchlist);
        }
        return watchlist;
    }

    @Override
    public void toAdd(InputData inputData, OutputData outputData) {
        Watchlist watchlist = getWatchlist(inputData);

        JSONMovie movie = inputData.get(JSONMovie.class, "movie");

        watchlist.addMovie(movie);

        AppAutowire.getInstance().getRepository(WatchlistRepository.class).save(watchlist);

        setData("watchlist", watchlist);
        this.sendData(outputData);
    }

    @Override
    public void toInit(InputData inputData, OutputData outputData) {
        Watchlist watchlist = getWatchlist(inputData);

        setData("watchlist", watchlist);

        this.sendData(outputData);
    }

    @Override
    public void toDelete(InputData inputData, OutputData outputData) {
        Watchlist watchlist = getWatchlist(inputData);

        String imdbID = inputData.get("imdbID");

        watchlist.removeMovie(imdbID);

        AppAutowire.getInstance().getRepository(WatchlistRepository.class).save(watchlist);

        setData("watchlist", watchlist);
        this.sendData(outputData);
    }

    @Override
    public void toEdit(InputData inputData, OutputData outputData) {
        Watchlist watchlist = getWatchlist(inputData);

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
}
