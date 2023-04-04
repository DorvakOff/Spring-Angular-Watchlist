import {Component, Input, OnInit} from '@angular/core';
import {JSONMovie} from "../../../models/OMDB";
import {UserService} from "../../../services/user.service";
import {WatchlistService} from "../../../services/watchlist.service";
import {NavigationService} from "../../../services/navigation.service";

@Component({
  selector: 'cmp-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.scss']
})
export class MovieListComponent implements OnInit {

  @Input() movies: JSONMovie[] = []
  @Input() watchlistButton: boolean = false

  constructor(public userService: UserService, public watchlistService: WatchlistService, private navigationService: NavigationService) { }

  ngOnInit(): void {
  }

  onMovieClick(movie: JSONMovie) {
    this.navigationService.navigate(`/movie/${movie.imdbID}`)
  }

  onErrorImage(event: any) {
    event.target.src = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1024px-No_image_available.svg.png"
  }
}
