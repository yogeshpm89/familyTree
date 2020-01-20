import { Component, OnInit } from '@angular/core';
import { LabelService } from '../services/label.service';
import { LocalStorageService, LocalStorageKey } from '../services/local-storage.service';
import { LoginService } from '../login/login.service';
import { NavigationService } from '../navigation.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  LABELS;
  isUserLoggedin = false;
  
  constructor(private labelService: LabelService,
    private loginService: LoginService,
    private navigationService: NavigationService,
    private localStorageService: LocalStorageService) { 
      this.localStorageService.userLoginSource$.subscribe(
        token => {
          if (token) {
            this.isUserLoggedin = true;
          } else {
            this.isUserLoggedin = false;
          }
        }
      )
  }

  ngOnInit() {
    this.LABELS = this.labelService.getLabels();
  }

  logout() {
    this.loginService.logout(this.localStorageService.get(LocalStorageKey.token)).subscribe(
      response => {
        this.localStorageService.set(LocalStorageKey.token, null);
        this.navigationService.openLoginPage();
      },
      error => {
      }
    );
  }
}
