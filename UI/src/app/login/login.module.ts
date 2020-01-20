import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginFormComponent } from './login-form/login-form.component';
import { LoginHomeComponent } from './login-home/login-home.component';
import { LoginRoutesModule } from './login-routes/login-routes.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [LoginFormComponent, LoginHomeComponent],
  imports: [
    CommonModule,
    FormsModule,
    LoginRoutesModule,
    HttpClientModule
  ]
})
export class LoginModule { }
