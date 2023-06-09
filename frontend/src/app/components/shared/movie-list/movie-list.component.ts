import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {JSONMovie} from "../../../models/OMDB";
import {UserService} from "../../../services/user.service";
import {WatchlistService} from "../../../services/watchlist.service";
import {NavigationService} from "../../../services/navigation.service";

@Component({
  selector: 'cmp-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.scss']
})
export class MovieListComponent implements OnInit, OnChanges {

  @Input() movies: JSONMovie[] = []
  @Input() watchlistButton: boolean = false
  @Input() showSearch: boolean = true
  sortedMovies: JSONMovie[] = []
  activeSort: any;
  filter: string = ''
  sortOptions: any[] = [
    {value: "Title", label: "SORT_TITLE"},
    {value: "Year", label: "SORT_YEAR"}
  ]

  constructor(public userService: UserService, public watchlistService: WatchlistService, private navigationService: NavigationService) {
  }

  ngOnInit(): void {
    this.sortMovies(false)
  }

  ngOnChanges(changes: SimpleChanges) {
    this.sortMovies(false)
  }


  onMovieClick(movie: JSONMovie) {
    this.navigationService.navigate(`/movie/${movie.imdbID}`)
  }

  onErrorImage(event: any) {
    event.target.src = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1024px-No_image_available.svg.png"
  }

  sortMovies(rotateSort: boolean = true) {
    if(!this.activeSort) {
      this.activeSort = this.sortOptions[0]
    }
    if (!this.showSearch) {
      this.sortedMovies = this.movies
      return
    }
    if (rotateSort) {
      let index = this.sortOptions.indexOf(this.activeSort)
      index = (index + 1) % this.sortOptions.length
      this.activeSort = this.sortOptions[index]
    }
    if (this.filter) {
      this.sortedMovies = this.movies.filter(movie => movie.title.toLowerCase().includes(this.filter.toLowerCase()))
    } else {
      this.sortedMovies = this.movies
    }
    switch (this.activeSort.value) {
      case "Title":
        this.sortedMovies.sort((a, b) => a.title.localeCompare(b.title))
        break;

      case "Year":
        this.sortedMovies.sort((a, b) => a.year.localeCompare(b.year))
        break;
    }
  }

  searchMovies($event: string) {
    this.filter = $event
    this.sortMovies(false)
  }
}
