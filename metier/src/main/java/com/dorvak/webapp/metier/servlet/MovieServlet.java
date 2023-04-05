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

public class MovieServlet extends WebServlet {

    @Override
    @NoAuth
    public void toInit(InputData inputData, OutputData outputData) {
        if (!inputData.has("imdbID")) {
            outputData.setError("Missing parameter: imdbID");
            this.sendData(outputData);
            return;
        }

        String imdbID = inputData.get("imdbID");
        float averageRating = AppAutowire.getInstance().getRepository(WatchlistRepository.class).getAverageRating(imdbID);

        setData("averageRating", averageRating);

        this.sendData(outputData);
    }

    public void toGetRating(InputData inputData, OutputData outputData) {
        if (!inputData.has("imdbID")) {
            outputData.setError("Missing parameter: imdbID");
            this.sendData(outputData);
            return;
        }

        String imdbID = inputData.get("imdbID");
        Watchlist watchlist = MovieManager.getWatchlist(inputData.getUser());
        JSONMovie movie = watchlist.getMovie(imdbID);

        if (movie == null) {
            outputData.setError("Movie not in watchlist");
            this.sendData(outputData);
            return;
        }

        setData("rating", movie.getRating());

        this.sendData(outputData);
    }

    public void toRateMovie(InputData inputData, OutputData outputData) {
        if (!inputData.has("imdbID")) {
            outputData.setError("Missing parameter: imdbID");
            this.sendData(outputData);
            return;
        }

        if (!inputData.has("rating")) {
            outputData.setError("Missing parameter: rating");
            this.sendData(outputData);
            return;
        }

        String imdbID = inputData.get("imdbID");
        int rating = inputData.getInt("rating");
        Watchlist watchlist = MovieManager.getWatchlist(inputData.getUser());
        JSONMovie movie = watchlist.getMovie(imdbID);

        if (movie == null) {
            outputData.setError("Movie not in watchlist");
            this.sendData(outputData);
            return;
        }

        movie.setRating(rating);
        AppAutowire.getInstance().getRepository(WatchlistRepository.class).save(watchlist);

        this.sendData(outputData);
    }
}
