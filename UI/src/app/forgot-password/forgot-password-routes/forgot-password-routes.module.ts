import { NgModule } from '@angular/core';
import { ForgotPasswordComponent } from '../forgot-password/forgot-password.component';
import { Routes, RouterModule } from '@angular/router';
import { SetPasswordComponent } from '../set-password/set-password.component';
import { ForgotPasswordHomeComponent } from '../forgot-password-home/forgot-password-home.component';
import { ROUTES } from 'src/app/constants/Constants';

const routes: Routes = [
  {
    path: '',
    component: ForgotPasswordHomeComponent,
    children: [
      {
        path: ROUTES.RESET_PASSWORD,
        component: ForgotPasswordComponent
      },
      {
      path: 'xyz',
      component: ForgotPasswordComponent
    }, {
      path: 'abc',
      component: SetPasswordComponent
    }]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ForgotPasswordRoutesModule { }