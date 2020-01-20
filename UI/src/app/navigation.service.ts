import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

const ROUTES = {
  HOME: 'home',
  LOGIN: 'login',
  FORGOT_PASSWORD: 'forgot-password',
  RESET_PASSWORD: 'resetp',
  RESET_PASSWORD_HOME: 'resetp/home',
  ADMIN: 'admin',
  USER: 'user',
  DATA: 'data',
  FORM: 'form',
  LIST: 'list'
}

@Injectable({
  providedIn: 'root'
})
export class NavigationService {

  constructor(private router: Router) { }

  openLoginPage() {
    this.router.navigate([ '/' + ROUTES.LOGIN]);
  }

  openAdminPage() {
    this.router.navigate([ '/' + ROUTES.ADMIN]);
  }

  openDataFormPage() {
    this.router.navigate([ '/' + ROUTES.DATA + '/' + ROUTES.FORM]);
  }

  openDataListPage() {
    this.router.navigate([ '/' + ROUTES.DATA + '/' + ROUTES.LIST]);
  }

  openForgotPasswordPage() {
    this.router.navigate(['/' + ROUTES.RESET_PASSWORD + '/xyz', {source: 'loginPage'}]);
  }

  openAdminUserManagementPage() {
    this.router.navigate([ '/' + ROUTES.ADMIN + '/' + ROUTES.USER]);
  }

}
