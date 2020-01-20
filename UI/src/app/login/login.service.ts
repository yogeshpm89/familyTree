import { Injectable } from '@angular/core';
import * as CryptoJS from 'crypto-js';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { urls, CLIENT_AUTHORIZATION } from '../constants/Constants';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(
    private http: HttpClient
  ) { }

  encryptPassword(plainText: string) {
    CryptoJS.pad.NoPadding = { pad: function () { }, unpad: function () { } };

    while (plainText.length < 32) {
      plainText = plainText + '0';
    }
    var key = CryptoJS.enc.Hex.parse("M4yPxWCNL9-2DZn7Hoguq-quScnAIsJo-Zgdqd53dk2-JjOFLdJ4tP");
    var iv = CryptoJS.enc.Hex.parse('00000000000000000000000000000000');

    var encrypted = CryptoJS.AES.encrypt(plainText, key, { iv: iv, padding: CryptoJS.pad.NoPadding });
    return encrypted.toString();
  }

  login(username, password): Observable<any> {
    const body = new HttpParams()
      .set('username', username) // 'yogeshpm89@gmail.com')
      .set('password', password) //'test') //'$2a$10$E69QfzqaPQQ9/IXtHUqG3uJy684trTAWyuphCkKnsrYpUtB4OPMle')
      .set('grant_type', 'password');

    const headers = new HttpHeaders()
                      .set('Content-Type', 'application/x-www-form-urlencoded')
                      .set('Authorization', CLIENT_AUTHORIZATION);
    return this.http.post(urls.TOKEN,
      body.toString(),
      {
        headers: headers
      }
    );
  }


  logout(token): Observable<any> {

    const body = new HttpParams()
      .set('access_token', token); // 'yogeshpm89@gmail.com')

    const headers = new HttpHeaders()
                      .set('Content-Type', 'application/x-www-form-urlencoded')
                      .set('Authorization', CLIENT_AUTHORIZATION);
    return this.http.delete(urls.REVOKE, { headers: headers, params: body });
  }
}
