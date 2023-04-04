import {Injectable} from '@angular/core';
import {JSONMovie, Watchlist} from "../models/OMDB";
import {ServletRequesterService} from "./servlet-requester.service";
import {AlertHandlerService} from "./alert-handler.service";

@Injectable({
  providedIn: 'root'
})
export class WatchlistService {

  watchlist?: Watchlist;
  otherWatchlist?: Watchlist;

  constructor(private servletRequesterService: ServletRequesterService, private alertHandler: AlertHandlerService) {
  }

  loadWatchlist() {
    this.servletRequesterService.requestAction('WatchlistServlet', 'init').subscribe(response => {
      this.watchlist = response.data.watchlist
    }, error => this.alertHandler.raiseError(error));
  }

  loadWatchlistById(id: string) {
    this.servletRequesterService.requestAction('WatchlistServlet', 'loadById', {id: id}).subscribe(response => {
      this.otherWatchlist = response.data.watchlist
    }, error => this.alertHandler.raiseError(error));
  }

  addToWatchlist(movie: JSONMovie) {
    let data = {
      movie: movie
    }
    this.servletRequesterService.requestAction('WatchlistServlet', 'add', data).subscribe(response => {
      this.watchlist = response.data.watchlist
    }, error => this.alertHandler.raiseError(error));
    if (this.watchlist) {
      this.watchlist.watchlistItems.push(movie);
    }
  }

  removeFromWatchlist(imdbID: string) {
    this.servletRequesterService.requestAction('WatchlistServlet', 'delete', {imdbID: imdbID}).subscribe(response => {
      this.watchlist = response.data.watchlist
    }, error => this.alertHandler.raiseError(error));
    if (this.watchlist) {
      this.watchlist.watchlistItems = this.watchlist.watchlistItems.filter(watchlistMovie => watchlistMovie.imdbID !== imdbID);
    }
  }

  isOnWatchlist(imdbID: string): boolean {
    if (!this.watchlist) return false;
    return this.watchlist.watchlistItems.some(watchlistMovie => watchlistMovie.imdbID === imdbID);
  }

  addOrRemoveFromWatchlist(movie: JSONMovie) {
    if (this.isOnWatchlist(movie.imdbID)) {
      this.removeFromWatchlist(movie.imdbID);
    } else {
      this.addToWatchlist({
        title: movie.title,
        year: movie.year,
        imdbID: movie.imdbID,
        poster: movie.poster
      });
    }
  }
}
