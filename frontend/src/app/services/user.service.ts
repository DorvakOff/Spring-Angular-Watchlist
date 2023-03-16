import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {User} from "../models/user";
import {Observable} from "rxjs";
import {WatchlistService} from "./watchlist.service";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  url = environment.api_url
  user?: User
  autoLoginLoading = false

  constructor(private http: HttpClient, private watchlistService: WatchlistService, private router: Router) {
    this.autoLogin()
  }

  private get headers(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    })
  }

  login(usernameOrEmail: string, password: string): Observable<string> {
    return this.http.post<string>(`${this.url}/login`, {
      username: usernameOrEmail,
      email: usernameOrEmail,
      password: password
    }, {
      responseType: "text" as "json"
    })
  }

  getUser(): Observable<User> {
    this.watchlistService.loadWatchlist()
    return this.http.get<User>(`${this.url}/user`, {headers: this.headers})
  }

  register(username: string, email: string, password: string): Observable<string> {
    return this.http.post<string>(`${this.url}/register`, {
      username: username,
      email: email,
      password: password
    }, {responseType: "text" as "json"})
  }

  auth(usernameOrEmail: string, password: string): Observable<User> {
    return new Observable(observer => {
      if (this.user) {
        observer.next(this.user)
        observer.complete()
        return
      }
      this.login(usernameOrEmail, password).subscribe(token => {
        localStorage.setItem('token', token)
        this.getUser().subscribe(user => {
          this.user = user
          observer.next(user)
          observer.complete()
        }, error => {
          observer.error(error)
        })
      }, error => {
        observer.error(error)
      })
    })
  }

  autoLogin() {
    if (!this.autoLoginLoading && !this.user && localStorage.getItem('token')) {
      this.autoLoginLoading = true
      this.watchlistService.loadWatchlist()
      this.getUser().subscribe(user => {
        this.user = user
        this.autoLoginLoading = false
      }, () => {
        this.autoLoginLoading = false
        localStorage.removeItem('token')
      })
    }
  }

  logout() {
    localStorage.removeItem('token')
    this.user = undefined
    this.router.navigateByUrl('/')
  }
}
