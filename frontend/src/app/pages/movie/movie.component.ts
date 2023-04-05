import {Component, OnInit} from '@angular/core';
import {OMDBService} from "../../services/omdb.service";
import {ActivatedRoute, Router} from "@angular/router";
import {OMDBMovie} from "../../models/OMDB";
import {WatchlistService} from "../../services/watchlist.service";
import {UserService} from "../../services/user.service";
import {ServletRequesterService} from "../../services/servlet-requester.service";

@Component({
  selector: 'cmp-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.scss']
})
export class MovieComponent implements OnInit {

  movie: OMDBMovie | undefined
  rating?: number
  averageRating?: number
  ratingCount?: number

  constructor(public userService: UserService, private omdb: OMDBService, private activatedRoute: ActivatedRoute, private router: Router, public watchlistService: WatchlistService, private servletRequester: ServletRequesterService) {
    this.activatedRoute.params.subscribe(params => {
      this.omdb.getMovieDetails(params['id']).subscribe(response => {
        if (response.Response === "False") {
          this.router.navigateByUrl(`/search?q=${params['id']}`)
        } else {
          this.movie = response
          this.servletRequester.requestAction('MovieServlet', 'init', {imdbID: this.movie?.imdbID}).subscribe(response => {
            this.averageRating = response.data.averageRating
            this.ratingCount = response.data.ratingCount
          })
          let interval = setInterval(() => {
            if (!this.userService.autoLoginLoading) {
              clearInterval(interval)
              if (this.userService.user) {
                this.servletRequester.requestAction('MovieServlet', 'getRating', {imdbID: this.movie?.imdbID}).subscribe(response => {
                  this.rating = response.data.rating
                })
              }
            }
          }, 1)
        }
      }, () => {
        this.router.navigateByUrl(`/search?q=${params['id']}`)
      })
    })
  }

  ngOnInit(): void {
  }

  getGenreAsList(): string[] {
    return this.movie?.Genre.split(', ') || []
  }

  onClickShare() {
    let shareOptions = document.querySelector('.share-options')
    if (!shareOptions) return
    shareOptions.classList.toggle('active')
  }

  getUrl() {
    return window.location.href
  }

  copyToClipboard() {
    let url = this.getUrl()
    navigator.clipboard.writeText(url)
  }

  addOrRemoveFromWatchlist(movie: OMDBMovie) {
    this.watchlistService.addOrRemoveFromWatchlist({
      title: movie.Title,
      year: movie.Year,
      imdbID: movie.imdbID,
      poster: movie.Poster
    })
  }

  handleMovieRated(stars: number) {
    this.rating = stars
    this.servletRequester.requestAction('MovieServlet', 'rateMovie', {
      imdbID: this.movie?.imdbID,
      rating: stars
    }).subscribe()
  }
}
