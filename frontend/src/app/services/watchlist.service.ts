import {Injectable} from '@angular/core';
import {JSONMovie} from "../models/OMDB";

@Injectable({
  providedIn: 'root'
})
export class WatchlistService {

  constructor() {
  }

  getWatchlist(): JSONMovie[] {
    let watchlist = JSON.parse(localStorage.getItem('watchlist') || '[]')

    // order by Title
    watchlist.sort((a: JSONMovie, b: JSONMovie) => {
      if (a.Title > b.Title) {
        return 1;
      } else if (a.Title < b.Title) {
        return -1;
      } else {
        return 0;
      }
    });
    return watchlist;
  }

  addToWatchlist(movie: JSONMovie) {
    let watchlist = this.getWatchlist();
    watchlist.push({
      Title: movie.Title,
      Year: movie.Year,
      imdbID: movie.imdbID,
      Poster: movie.Poster
    });
    localStorage.setItem('watchlist', JSON.stringify(watchlist));
  }

  removeFromWatchlist(imdbID: string) {
    let watchlist = this.getWatchlist();
    watchlist = watchlist.filter(watchlistMovie => watchlistMovie.imdbID !== imdbID);
    localStorage.setItem('watchlist', JSON.stringify(watchlist));
  }

  isOnWatchlist(imdbID: string): boolean {
    let watchlist = this.getWatchlist();
    return watchlist.some(watchlistMovie => watchlistMovie.imdbID === imdbID);
  }

  addOrRemoveFromWatchlist(movie: JSONMovie) {
    if (this.isOnWatchlist(movie.imdbID)) {
      this.removeFromWatchlist(movie.imdbID);
    } else {
      this.addToWatchlist({
        Title: movie.Title,
        Year: movie.Year,
        imdbID: movie.imdbID,
        Poster: movie.Poster
      });
    }
  }
}
