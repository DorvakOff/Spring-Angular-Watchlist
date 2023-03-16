import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {AlertHandlerService} from "./alert-handler.service";
import {ServletResponse} from "../models/servlet";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ServletRequesterService {

  url = environment.api_url

  constructor(private http: HttpClient, private alertHandler: AlertHandlerService) {
  }

  requestAction(servlet: string, action: string): Observable<ServletResponse> {
    return new Observable<ServletResponse>(subscriber => {
      this.http.post<ServletResponse>(`${this.url}/api/servlet/execute`, {
        servlet: servlet,
        action: action
      }).subscribe(response => {
        this.handlePossibleError(response)
        subscriber.next(response)
      })
    })
  }

  private handlePossibleError(response: ServletResponse) {
    if (response.error && response.data.error) {
      this.alertHandler.raiseError(response.data.error)
    }
  }
}

