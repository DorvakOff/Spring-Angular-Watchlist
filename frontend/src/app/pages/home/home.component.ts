import { Component, OnInit } from '@angular/core';
import {TrendsService} from "../../services/trends.service";
import {JSONMovie} from "../../models/OMDB";
import {Router} from "@angular/router";

@Component({
  selector: 'cmp-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  mostPopularMovies: JSONMovie[] = []

  constructor(private trendsService: TrendsService, private router: Router) { }

  ngOnInit(): void {
    this.trendsService.getPopular().subscribe((data) => {
      this.mostPopularMovies = data
    })
  }

  onClickMovie(movie: JSONMovie) {
    this.router.navigate(['/movie', movie.imdbID])
  }
}
