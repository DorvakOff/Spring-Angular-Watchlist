import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {JSONMovie} from "../models/OMDB";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class TrendsService {

  url = environment.api_url

  constructor(private http: HttpClient) {
  }

  getPopular(): Observable<JSONMovie[]> {
    return this.http.get<JSONMovie[]>(this.url + '/trends/popular')
  }
}
