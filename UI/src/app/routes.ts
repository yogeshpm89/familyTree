import { Routes } from '@angular/router';

export const appRoutes: Routes = [
    {
        path: 'login',
        canActivate: [],
        loadChildren: 'src/app/login/login.module#LoginModule'
    },
    {
        path: 'admin',
        canActivate: [],
        loadChildren: 'src/app/admin/admin.module#AdminModule'
    },
    {
        path: 'resetp',
        canActivate: [],
        loadChildren: 'src/app/forgot-password/forgot-password.module#ForgotPasswordModule'
    },
    {
        path: 'data',
        canActivate: [],
        loadChildren: 'src/app/data-form/data-form.module#DataFormModule'
    },
    { path: '', redirectTo:'/login', pathMatch: 'full' }
];