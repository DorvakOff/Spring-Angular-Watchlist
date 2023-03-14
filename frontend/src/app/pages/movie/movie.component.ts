import {Component, OnInit} from '@angular/core';
import {OMDBService} from "../../services/omdb.service";
import {ActivatedRoute, Router} from "@angular/router";
import {OMDBMovie} from "../../models/OMDB";
import {WatchlistService} from "../../services/watchlist.service";

@Component({
  selector: 'cmp-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.scss']
})
export class MovieComponent implements OnInit {

  movie: OMDBMovie | undefined

  constructor(private omdb: OMDBService, private activatedRoute: ActivatedRoute, private router: Router, public watchlistService: WatchlistService) {
    this.activatedRoute.params.subscribe(params => {
      this.omdb.getMovieDetails(params['id']).subscribe(response => {
        if (response.Response === "False") {
          this.router.navigateByUrl(`/search?q=${params['id']}`)
        } else {
          this.movie = response
          console.log(this.movie)
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
}
