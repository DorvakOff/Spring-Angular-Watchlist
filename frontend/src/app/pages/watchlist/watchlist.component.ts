import {Component, OnInit} from '@angular/core';
import {JSONMovie} from "../../models/OMDB";
import {Router} from "@angular/router";
import {WatchlistService} from "../../services/watchlist.service";
import {UserService} from "../../services/user.service";
import {ServletRequesterService} from "../../services/servlet-requester.service";

@Component({
  selector: 'cmp-watchlist',
  templateUrl: './watchlist.component.html',
  styleUrls: ['./watchlist.component.scss']
})
export class WatchlistComponent implements OnInit {

  constructor(private router: Router, public watchlistService: WatchlistService, public userService: UserService, private servletRequester: ServletRequesterService) {
    let interval = setInterval(() => {
      if (!this.userService.autoLoginLoading) {
        clearInterval(interval)
        if (!this.userService.user) {
          this.router.navigateByUrl('/login?redirect=/watchlist')
          return
        }
      }
    }, 1)
  }

  ngOnInit(): void {
  }

  onErrorImage(event: any) {
    event.target.src = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1024px-No_image_available.svg.png"
  }

  onMovieClick(movie: JSONMovie) {
    this.router.navigate(['/movie', movie.imdbID])
  }

  toggleVisibility() {
    if (!this.watchlistService.watchlist) return
    let visibility = !this.watchlistService.watchlist.publicList
    this.watchlistService.watchlist.publicList = visibility
    this.servletRequester.requestAction('WatchlistServlet', 'edit', {publicList: visibility}).subscribe();
  }

  openShare() {
    window.open(window.location.href + '/' + this.userService.user?.userId, '_blank')
  }
}
