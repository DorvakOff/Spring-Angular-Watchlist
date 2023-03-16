import {Component, OnInit} from '@angular/core';
import {JSONMovie} from "../../models/OMDB";
import {Router} from "@angular/router";
import {WatchlistService} from "../../services/watchlist.service";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'cmp-watchlist',
  templateUrl: './watchlist.component.html',
  styleUrls: ['./watchlist.component.scss']
})
export class WatchlistComponent implements OnInit {

  constructor(private router: Router, public watchlistService: WatchlistService, private userService: UserService) {
    if(!this.userService.user) {
      this.router.navigateByUrl('/login?redirect=/watchlist')
    }
    if (!this.watchlistService.watchlist) {
      this.watchlistService.loadWatchlist()
    }
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
