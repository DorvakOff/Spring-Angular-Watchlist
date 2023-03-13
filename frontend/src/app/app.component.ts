import { Component } from '@angular/core';
import {SearchResult} from "./components/shared/searchbar/searchbar.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'frontend';
  searchResults: SearchResult[] = []

  constructor() {
    for (let i = 0; i < 50; i++) {
      this.searchResults.push({
        title: `Test ${i}`,
        url: '/test'
      })
    }
  }
}
