import { GlobalService } from './../../services/global.service';
import { User, UserType } from 'src/app/models/user';
import { UserService } from './../../user/user.service';
import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login.service';
import { LocalStorageService, LocalStorageKey } from 'src/app/services/local-storage.service';
import { NavigationService } from 'src/app/navigation.service';
import { AppMessageService, MESSAGE_TYPE } from 'src/app/message/message.service';
import { ERROR_MSG } from 'src/app/constants/Constants';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {

  email: string;
  password: string;

  constructor(
    private loginService: LoginService,
    private localStorageService: LocalStorageService,
    private navigationService: NavigationService,
    private messageService: AppMessageService,
    private userService: UserService,
    private globalService: GlobalService
  ) { 
  }

  ngOnInit() {
  }

  resetForm() {
    this.email = null;
    this.password = null;
  }

  ping() {
    // this.loginService.ping()
  }

  onResetPassword() {
    this.navigationService.openForgotPasswordPage()
    
  }

  onSubmit() {
    let requestJson = {
      username: this.email,
      password: this.password
    }
    this.loginService.login(this.email, this.password).subscribe(
      response => {
        this.localStorageService.set(LocalStorageKey.token, response['access_token']);
        this.getUserDetails();
      },
      error => {
         this.resetForm();
      }
    );
  }

  getUserDetails() {
    this.userService.getUser(this.email).subscribe(
      response => {
        if (response && response['data'] && response['data'].length > 0) {
          const user:User = response['data'][0];
          if (user.role === UserType.ADMIN) {
            this.navigationService.openAdminPage();
          } else if (user.role === UserType.USER) {
            this.navigationService.openDataListPage();
          } 
        }

        this.globalService.getAllCountries();
        this.globalService.getAllStates();
        this.globalService.getAllCities();
      }
    )
  }

}
