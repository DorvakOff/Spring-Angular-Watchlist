package com.dorvak.webapp.metier.models;

import com.dorvak.webapp.moteur.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Watchlist {


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<JSONMovie> watchlistItems;
    @Id
    private String ownerID;

    private String description;

    private boolean publicList;

    public Watchlist() {
    }

    public Watchlist(List<JSONMovie> watchlistItems, User owner) {
        this.watchlistItems = watchlistItems;
        this.ownerID = owner.getUserId();
        this.publicList = false;
        this.description = owner.getUsername() + "'s watchlist";
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

    public boolean isPublicList() {
        return publicList;
    }

    public void setPublicList(boolean publicList) {
        this.publicList = publicList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
