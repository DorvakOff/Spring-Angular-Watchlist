import {Component, OnInit} from '@angular/core';
import {JSONMovie} from "../../models/OMDB";
import {Router} from "@angular/router";
import {ServletRequesterService} from "../../services/servlet-requester.service";

@Component({
  selector: 'cmp-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  mostPopularMovies: JSONMovie[] = []

  constructor(private servletRequester: ServletRequesterService, private router: Router) {
  }

  ngOnInit(): void {
    this.servletRequester.requestAction('TrendsServlet', 'init').subscribe(response => {
      this.mostPopularMovies = response.data.popular
    })
  }

  onClickMovie(movie: JSONMovie) {
    this.router.navigate(['/movie', movie.imdbID])
  }
}
