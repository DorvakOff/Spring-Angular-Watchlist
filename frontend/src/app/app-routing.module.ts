import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MovieComponent} from "./pages/movie/movie.component";
import {SearchComponent} from "./pages/search/search.component";
import {WatchlistComponent} from "./pages/watchlist/watchlist.component";

const routes: Routes = [
  {path: 'search', component: SearchComponent},
  {path: 'movie/:id', component: MovieComponent},
  {path: 'watchlist', component: WatchlistComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
