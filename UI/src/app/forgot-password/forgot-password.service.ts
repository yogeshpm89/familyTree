import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { urls } from '../constants/Constants';

@Injectable({
  providedIn: 'root'
})
export class ForgotPasswordService {

  constructor(private httpClient: HttpClient) { }

  resetPassword(username: string, password: string): Observable<Object> {
    const requestBody = {
        username: username,
        password: password
    };
    return this.httpClient.post(urls.USER.RESET_PASSWORD, requestBody);
  }

  forgotPassword(username: string): Observable<Object> {
    const requestBody = {
        username: username,
    };
    return this.httpClient.post(urls.USER.FORGOT_PASSWORD, requestBody);
  }
}
