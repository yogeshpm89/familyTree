import { Component, OnInit } from '@angular/core';
import { AppBlockUiService } from './app-block-ui.service';

@Component({
  selector: 'app-app-block-ui',
  templateUrl: './app-block-ui.component.html',
  styleUrls: ['./app-block-ui.component.scss']
})
export class AppBlockUiComponent implements OnInit {

  blocked: boolean;

  constructor(private appBlockUiService: AppBlockUiService) {
    this.appBlockUiService.blockUiSource$.subscribe(
      flag => {
        // 0 - un-blocking ui
        // 1 - blocking ui
        if (flag === '0') {
            console.log('um-blocking ui');
            this.blocked = false;
        } else if (flag === '1') {
            console.log('blocking ui');
            this.blocked = true;
        }
      }
    );
  }

  ngOnInit() {
  }

}
