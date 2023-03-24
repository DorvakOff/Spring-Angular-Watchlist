package com.dorvak.webapp.metier.models;

import jakarta.persistence.Entity;

import java.time.Instant;

@Entity
public class TrendingJsonMovie extends JSONMovie {

    private int rank;
    private Instant date;

    public TrendingJsonMovie() {
    }

    public TrendingJsonMovie(int rank, JSONMovie jsonMovie) {
        super(jsonMovie.getTitle(), jsonMovie.getYear(), jsonMovie.getImdbID(), jsonMovie.getPoster());
        this.rank = rank;
        this.date = Instant.now();
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }
}
