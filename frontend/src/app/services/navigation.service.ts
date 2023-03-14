import {Injectable} from '@angular/core';
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class NavigationService {

  constructor(private router: Router) {
  }

  navigate(url: string) {
    this.navigateWithProps(url, {})
  }

  navigateWithProps(url: string, props: {}) {
    this.router.navigateByUrl(url, {state: props})
  }
}
