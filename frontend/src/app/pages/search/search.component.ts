import {Component, OnInit} from '@angular/core';
import {JSONMovie, OMDBMovie, OMDBSearchResult} from "../../models/OMDB";
import {OMDBService} from "../../services/omdb.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'cmp-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {

  searchResult?: OMDBSearchResult
  keywords: string = '';
  movies: JSONMovie[] = []

  constructor(public omdb: OMDBService, private activatedRoute: ActivatedRoute) {
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
      this.searchResult = response
      this.movies = response.Search.map((movie: OMDBMovie) => {
        return {
          title: movie.Title,
          year: movie.Year,
          imdbID: movie.imdbID,
          poster: movie.Poster
        }
      })
    })
  }
}
