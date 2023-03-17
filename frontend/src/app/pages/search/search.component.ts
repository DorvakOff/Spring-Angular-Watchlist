import {Component, OnInit} from '@angular/core';
import {OMDBMovie, OMDBSearchResult} from "../../models/OMDB";
import {OMDBService} from "../../services/omdb.service";
import {ActivatedRoute, Router} from "@angular/router";
import {WatchlistService} from "../../services/watchlist.service";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'cmp-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {

  searchResult?: OMDBSearchResult
  keywords: string = '';

  constructor(public userService: UserService, public omdb: OMDBService, private activatedRoute: ActivatedRoute, private router: Router, public watchlistService: WatchlistService) {
  }

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe(params => {
      if (params['q']) {
        this.keywords = params['q']
        this.search(this.keywords)
      }
    })
  }

  search(keyword: string) {
    window.history.pushState({}, "", `/search?q=${keyword}`)
    this.omdb.getMovies(keyword).subscribe(response => {
      if (response.Response === "False") {
        this.searchResult = undefined
        return
      }
      response.Search = response.Search.filter(movie => movie.Poster !== "N/A")
      this.searchResult = response
    })
  }

  onErrorImage(event: any) {
    event.target.src = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1024px-No_image_available.svg.png"
  }

  onMovieClick(movie: OMDBMovie) {
    this.router.navigate(['/movie', movie.imdbID])
  }

}
