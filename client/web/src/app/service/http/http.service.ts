import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class HttpService {
  API_URL = environment.apiUrl;

  constructor(private httpClient: HttpClient) { }

  get<T>(path: string) {
    return this.httpClient.get<T>(this.API_URL + path, {
      observe: 'response'
    });
  }

  post<T, R>(path: string, body: T) {
    return this.httpClient.post<R>(this.API_URL + path, body, {
      observe: 'response'
    });
  }
}
