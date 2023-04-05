import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SearchbarComponent } from './components/shared/searchbar/searchbar.component';
import {FormsModule} from "@angular/forms";
import { ButtonComponent } from './components/shared/button/button.component';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import { MovieComponent } from './pages/movie/movie.component';
import { SearchComponent } from './pages/search/search.component';
import {MenuComponent} from "./components/menu/menu.component";
import {AlertComponent} from "./components/alerts/alert/alert.component";
import {AlertBoxComponent} from "./components/alerts/alert-box/alert-box.component";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import { WatchlistComponent } from './pages/watchlist/watchlist.component';
import { LoginComponent } from './pages/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { MovieListComponent } from './components/shared/movie-list/movie-list.component';
import { StarRatingComponent } from './components/shared/star-rating/star-rating.component';
import { ShareButtonComponent } from './components/shared/share-button/share-button.component';

@NgModule({
  declarations: [
    AppComponent,
    SearchbarComponent,
    ButtonComponent,
    MovieComponent,
    SearchComponent,
    MenuComponent,
    AlertComponent,
    AlertBoxComponent,
    WatchlistComponent,
    LoginComponent,
    HomeComponent,
    MovieListComponent,
    StarRatingComponent,
    ShareButtonComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

export function HttpLoaderFactory(httpClient: HttpClient) {
  return new TranslateHttpLoader(httpClient)
}
