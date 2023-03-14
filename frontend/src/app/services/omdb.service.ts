import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {OMDBMovie, OMDBSearchResult} from "../models/OMDB";

const PARAMS = new HttpParams({
  fromObject: {
    action: "opensearch",
    format: "json",
    origin: "*"
  }
});

@Injectable({
  providedIn: 'root'
})
export class OMDBService {


  apiKey = environment.api_key

  constructor(private http: HttpClient) { }

  getMovies(keyword: string): Observable<OMDBSearchResult> {
    return this.http.get<OMDBSearchResult>(`https://omdbapi.com/?apikey=${this.apiKey}&s=${keyword}`, { params: PARAMS.set('search', keyword) });
  }

  getMovieDetails(imdbID: string): Observable<OMDBMovie> {
    return this.http.get<OMDBMovie>(`https://omdbapi.com/?apikey=${this.apiKey}&i=${imdbID}&plot=full`, { params: PARAMS.set('search', imdbID) });
  }
}
