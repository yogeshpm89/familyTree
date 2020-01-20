import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { REQUEST } from '../constants/Constants';

@Injectable({
  providedIn: 'root'
})
export class GridService {

  constructor(private httpClient: HttpClient) { }

  getDataList(url, pageNumber, pageSize, orderBy, orderType, filterArray): Observable<Object> {
    const requestBody = {
      FILTER: filterArray
    };
    
    requestBody[REQUEST.PAGE_NO]=pageNumber;
    requestBody[REQUEST.PAGE_SIZE]=pageSize;

    requestBody[REQUEST.ORDER_BY]= orderBy;
    requestBody[REQUEST.ORDER_TYPE]= orderType;

    const request = this.httpClient.post(url, requestBody);
    return request;
  }
}
