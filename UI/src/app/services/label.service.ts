import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LabelService {

  constructor() { }

  getLabels(lang ? : string) {
    if (lang === 'eng' || !lang)  {
        return LABELS;
    } else {
        return LABELS;
    }

}
}


const LABELS = {
  applicationName: 'Family Tree',
  adminTabs: {
    users: 'Users',
    config: 'Config'
  }
}
