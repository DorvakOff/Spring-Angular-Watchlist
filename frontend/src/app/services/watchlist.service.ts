import {Injectable} from '@angular/core';
import {JSONMovie, OMDBMovie} from "../models/OMDB";
import {ServletRequesterService} from "./servlet-requester.service";
import {AlertHandlerService} from "./alert-handler.service";

@Injectable({
  providedIn: 'root'
})
export class WatchlistService {

  watchlist: JSONMovie[] = [];

  constructor(private servletRequesterService: ServletRequesterService, private alertHandler: AlertHandlerService) {
  }

  loadWatchlist() {
    this.servletRequesterService.requestAction('WatchlistServlet', 'init').subscribe(response => {
      this.watchlist = response.data.watchlist
    }, error => this.alertHandler.raiseError(error));
  }

  addToWatchlist(movie: JSONMovie) {
    let data = {
      movie: movie
    }
    this.servletRequesterService.requestAction('WatchlistServlet', 'add', data).subscribe(response => {
      this.watchlist = response.data.watchlist
    }, error => this.alertHandler.raiseError(error));
    this.watchlist.push(movie);
  }

  removeFromWatchlist(imdbID: string) {
    this.servletRequesterService.requestAction('WatchlistServlet', 'delete', {imdbID: imdbID}).subscribe(response => {
      this.watchlist = response.data.watchlist
    }, error => this.alertHandler.raiseError(error));
    this.watchlist = this.watchlist.filter(watchlistMovie => watchlistMovie.imdbID !== imdbID);
  }

  isOnWatchlist(imdbID: string): boolean {
    if (!this.watchlist) return false;
    return this.watchlist.some(watchlistMovie => watchlistMovie.imdbID === imdbID);
  }

  addOrRemoveFromWatchlist(movie: OMDBMovie) {
    if (this.isOnWatchlist(movie.imdbID)) {
      this.removeFromWatchlist(movie.imdbID);
    } else {
      this.addToWatchlist({
        title: movie.Title,
        year: movie.Year,
        imdbID: movie.imdbID,
        poster: movie.Poster
      });
    }
  }
}
