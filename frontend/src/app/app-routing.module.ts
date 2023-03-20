import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MovieComponent} from "./pages/movie/movie.component";
import {SearchComponent} from "./pages/search/search.component";
import {WatchlistComponent} from "./pages/watchlist/watchlist.component";
import {LoginComponent} from "./pages/login/login.component";
import {HomeComponent} from "./pages/home/home.component";

const routes: Routes = [
  {path: 'search', component: SearchComponent},
  {path: 'movie/:id', component: MovieComponent},
  {path: 'watchlist', component: WatchlistComponent},
  {path: 'login', component: LoginComponent},
  {path: '', component: HomeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
