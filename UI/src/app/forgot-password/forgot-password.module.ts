import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { ForgotPasswordRoutesModule } from './forgot-password-routes/forgot-password-routes.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ForgotPasswordHomeComponent } from './forgot-password-home/forgot-password-home.component';
import { SetPasswordComponent } from './set-password/set-password.component';

@NgModule({
  imports: [
    CommonModule,
    ForgotPasswordRoutesModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [ForgotPasswordComponent, ForgotPasswordHomeComponent, SetPasswordComponent]
})
export class ForgotPasswordModule { }
