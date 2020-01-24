import { urls } from 'src/app/constants/Constants';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private httpClient: HttpClient) { }

  uploadFile(body: FormData): Observable<Object> {
    // body.append('loggedinUser',  this.loginTokenService.getLoggedinUser().email);
    return this.httpClient.post(urls.DATA.SAVE, body);
  }

  getImage(imageName: string): Observable<Object> {
    let url = urls.DATA.GET;
    url = url.replace('{fileName}', imageName);
    return this.httpClient.get(url);
  }
}
