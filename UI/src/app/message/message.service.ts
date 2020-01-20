import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AppMessageService {

  private showMessageSource = new Subject<Message>();
  showMessageSource$ = this.showMessageSource.asObservable();
  
  constructor() { }

  showMessage(type: string, text: string) {
    let summary = '';
    if (type === MESSAGE_TYPE.SUCCESS) {
      summary = 'Success';
    } else if (type === MESSAGE_TYPE.INFO) {
      summary = 'Information';
    } else if (type === MESSAGE_TYPE.WARNING) {
      summary = 'Warning';
    } else if (type === MESSAGE_TYPE.ERROR) {
      summary = 'Error';
    }
    const msg = new Message(type, summary, text);
    this.showMessageSource.next(msg);
  }
}


export class Message {
  severity: string;
  summary: string;
  detail: string;

  constructor(severity: string, summary: string, detail: string) {
    this.severity = severity;
    this.summary = summary;
    this.detail = detail;
  }
}

export const MESSAGE_TYPE = {
  SUCCESS : 'success',
  INFO : 'info',
  WARNING : 'warn',
  ERROR : 'error'
}