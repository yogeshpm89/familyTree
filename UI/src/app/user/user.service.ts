import { Filter } from './../app-grid/grid/grid.component';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from 'src/app/models/user';
import { Observable } from 'rxjs';
import { urls, REQUEST } from 'src/app/constants/Constants';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  saveUser(user: User): Observable<Object> {
    let url = urls.USER.SAVE;
    if (user.userId) {
      url = url + "/" + user.userId;
      return this.http.put(url, user);
    }    
    return this.http.post(url, user);
  }

  deleteUser(userId: number): Observable<Object> {
    let url = urls.USER.DELETE.replace('{userId}', '' + userId);
    return this.http.delete(url);
  }

  undeleteUser(userId: number): Observable<Object> {
    let url = urls.USER.UNDELETE.replace('{userId}', '' + userId);
    return this.http.post(url, {});
  }

  getUser(userEmail: string): Observable<Object> {
    let url = urls.USER.GET;

    const filterArray = [];
    const filter = new Filter();
    filter.FILTER_KEY = 'email';
    filter.FILTER_VALUE = userEmail;
    filter.FILTER_TYPE = REQUEST.FILTER_TYPE_CHARS;
    filterArray.push(filter);

    const request = { "FILTER": filterArray }
    return this.http.post(url, request);
  }
  
}
