import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {

  constructor() { }

  private userLoginSource = new Subject<String>();
  userLoginSource$ = this.userLoginSource.asObservable();

  set(key: string, value: string ) {
    window.localStorage.setItem(key, value);

    if (key === LocalStorageKey.token) {
      this.userLoginSource.next(value);
    }
  }

  get(key: string) {
    return window.localStorage.getItem(key);
  }
}

export const LocalStorageKey = {
  token: 'family_tree_token'
}
