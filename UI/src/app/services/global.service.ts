import { City } from './../models/City';
import { State } from './../models/State';
import { Country } from './../models/Country';
import { urls } from 'src/app/constants/Constants';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GlobalService {

  private countryList: Country[] = [];
  private stateList: State[] = [];
  private cityList: City[] = [];
  constructor(private httpClient: HttpClient) { }

  private setCountryList(list) {
    this.countryList = list;
  }

  private setStateList(list) {
    this.stateList = list;
  }

  private setCityList(list) {
    this.cityList = list;
  }

  getCountryList() {
    return this.countryList;
  }

  getStateList() {
    return this.stateList;
  }

  getCityList() {
    return this.cityList;
  }

  getAllCountries() {
    this.httpClient.post(urls.REF.COUNTRY, {}).subscribe(
      response => {
        if (response && response['data']) {
          this.setCountryList(response['data']);
        }
      }
    );
  };

  getAllStates() {
    this.httpClient.post(urls.REF.STATE, {}).subscribe(
      response => {
        if (response && response['data']) {
          this.setStateList(response['data']);
        }
      }
    );
  }

  getAllCities() {
    this.httpClient.post(urls.REF.CITY, {}).subscribe(
      response => {
        if (response && response['data']) {
          this.setCityList(response['data']);
        }
      }
    );
  }
}
