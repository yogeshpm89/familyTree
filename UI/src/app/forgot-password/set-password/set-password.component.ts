import { Component, OnInit } from '@angular/core';
import { AppMessageService, MESSAGE_TYPE } from 'src/app/message/message.service';
import { ForgotPasswordService } from '../forgot-password.service';
import { ActivatedRoute, Params } from '@angular/router';
import { LoginService } from 'src/app/login/login.service';
import { NavigationService } from 'src/app/navigation.service';

@Component({
  selector: 'app-set-password',
  templateUrl: './set-password.component.html',
  styleUrls: ['./set-password.component.css']
})
export class SetPasswordComponent implements OnInit {

  email: string;
  password: string;
  cPassword: string;

  disabled = {
    email : false,
    password: false,
    cPassword: false
  }

  constructor(private loginService: LoginService,
    private forgotPasswordService: ForgotPasswordService,
    private activatedRoute: ActivatedRoute,
    private navigationService: NavigationService,
    private appMessageService: AppMessageService) { }

  ngOnInit() {
    this.activatedRoute.params.forEach(
      (params: Params) => {
        if (params['username']) {
          const username = params['username'];
          this.email = atob(username);
          this.disabled.email = true;
        }
      }
      
    )
  }

  onSubmit() {
    
    if (this.password !== this.cPassword) {

    }
    const p = this.loginService.encryptPassword(this.password);
    this.forgotPasswordService.resetPassword(this.email, this.password).subscribe(
      response => {
        const status = response['status'];
        const reason = response['reason'];

        if (!status) {
          this.appMessageService.showMessage(MESSAGE_TYPE.ERROR, reason);
        } else {
          this.appMessageService.showMessage(MESSAGE_TYPE.SUCCESS, reason);
          this.navigationService.openLoginPage();
        }
      }
    );
  }
}
