import { Component, OnInit } from '@angular/core';
import { FormBuilder, AbstractControl } from '@angular/forms';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { AppMessageService, MESSAGE_TYPE } from 'src/app/message/message.service';
import { LoginService } from 'src/app/login/login.service';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { NavigationService } from 'src/app/navigation.service';
import { ForgotPasswordService } from '../forgot-password.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  email: string;
  validity: {username:boolean, password: boolean, password1: boolean};

  constructor(private router: Router,
    private appMessageService: AppMessageService,
    private loginService: LoginService,
    private localStorageService: LocalStorageService,
    private activatedRoute: ActivatedRoute,
    private navigation: NavigationService,
    private forgotPasswordService: ForgotPasswordService,
    private formBuilder: FormBuilder) { }

  
  ngOnInit() {
  }

  onSubmit() {
    this.forgotPasswordService.forgotPassword(this.email).subscribe(
      response => {
        const status = response['status'];
        const reason = response['reason'];

        if (!status) {
          this.appMessageService.showMessage(MESSAGE_TYPE.ERROR, reason);
        } else {
          this.appMessageService.showMessage(MESSAGE_TYPE.SUCCESS, reason);
        }
      }
    );
  }
}


export class PasswordValidation {

  static MatchPassword(AC: AbstractControl) {
     let password = AC.get('password').value; // to get value in input tag
     let confirmPassword = AC.get('cPassword').value; // to get value in input tag
      if(password != confirmPassword) {
          if(confirmPassword != '')
            AC.get('cPassword').setErrors( {MatchPassword: true} )

      } else {
          console.log('true');
          return null
      }
  }
}