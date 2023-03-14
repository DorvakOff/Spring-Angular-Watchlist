import { Component, OnInit } from '@angular/core';
import {JSONMovie} from "../../models/OMDB";
import {Router} from "@angular/router";
import {WatchlistService} from "../../services/watchlist.service";

@Component({
  selector: 'cmp-watchlist',
  templateUrl: './watchlist.component.html',
  styleUrls: ['./watchlist.component.scss']
})
export class WatchlistComponent implements OnInit {

  watchlist: JSONMovie[] = []

  constructor(private router: Router, private watchlistService: WatchlistService) {
    this.watchlist = watchlistService.getWatchlist()
  }

  ngOnInit(): void {
  }

  onErrorImage(event: any) {
    event.target.src = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1024px-No_image_available.svg.png"
  }

  onMovieClick(movie: JSONMovie) {
    this.router.navigate(['/movie', movie.imdbID])
  }
}
