package com.dorvak.webapp.metier.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
public class Watchlist {

    @Id
    @GeneratedValue(generator = "snowflake_generator")
    @GenericGenerator(name = "snowflake_generator", strategy = "com.dorvak.webapp.moteur.generators.SnowflakeGenerator")
    private String id;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<JSONMovie> watchlistItems;
    private String ownerID;

    public Watchlist() {
    }

    public Watchlist(List<JSONMovie> watchlistItems, String ownerID) {
        this.watchlistItems = watchlistItems;
        this.ownerID = ownerID;
    }

    public List<JSONMovie> getWatchlistItems() {
        return watchlistItems;
    }

    public void setWatchlistItems(List<JSONMovie> watchlist) {
        this.watchlistItems = watchlist;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public void addMovie(JSONMovie movie) {
        this.watchlistItems.add(movie);
    }

    public void removeMovie(String imdbID) {
        this.watchlistItems.removeIf(movie -> movie.getImdbID().equals(imdbID));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
