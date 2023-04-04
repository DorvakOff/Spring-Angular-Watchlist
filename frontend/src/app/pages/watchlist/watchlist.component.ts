import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {WatchlistService} from "../../services/watchlist.service";
import {UserService} from "../../services/user.service";
import {ServletRequesterService} from "../../services/servlet-requester.service";
import {NavigationService} from "../../services/navigation.service";
import {debounceTime, distinctUntilChanged, Subject, Subscription} from "rxjs";

@Component({
  selector: 'cmp-watchlist',
  templateUrl: './watchlist.component.html',
  styleUrls: ['./watchlist.component.scss']
})
export class WatchlistComponent implements OnInit, OnDestroy {

  watchlistId?: string
  editDescriptionSubject = new Subject<string | undefined>()
  editDescriptionSubscription?: Subscription

  constructor(private navigationService: NavigationService, public watchlistService: WatchlistService, public userService: UserService, private servletRequester: ServletRequesterService, activatedRoute: ActivatedRoute) {
    this.watchlistId = activatedRoute.snapshot.params['id']
    let interval = setInterval(() => {
      if (!this.userService.autoLoginLoading) {
        clearInterval(interval)
        if (!this.userService.user) {
          this.navigationService.navigate('/login?redirect=/watchlist' + (this.watchlistId ? '/' + this.watchlistId : ''))
          return
        }
        if (this.watchlistId) {
          this.watchlistService.loadWatchlistById(this.watchlistId)
        }
      }
    }, 1)
  }

  public onDescriptionChanged(event: Event): void {
    const searchQuery = (event.target as HTMLInputElement).value;
    this.editDescriptionSubject.next(searchQuery?.trim());
  }

  ngOnInit(): void {
    this.editDescriptionSubscription = this.editDescriptionSubject
      .pipe(
        debounceTime(300),
        distinctUntilChanged(),
      )
      .subscribe((results) => {
        if (results && this.watchlistService.watchlist) {
          this.watchlistService.watchlist.description = results
          this.servletRequester.requestAction('WatchlistServlet', 'edit', {description: results}).subscribe();
        }
      });
  }

  ngOnDestroy(): void {
    this.editDescriptionSubscription?.unsubscribe()
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
