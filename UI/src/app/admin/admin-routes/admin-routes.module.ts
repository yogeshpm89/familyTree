import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminHomeComponent } from '../admin-home/admin-home.component';
import { ListComponent } from 'src/app/user/list/list.component';


const routes: Routes = [
  {
    path: '',
    component: AdminHomeComponent,
    children: [
      { path: 'form', component: AdminHomeComponent },
      { path: 'user', component:  ListComponent }
    ]
  }
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutesModule { }
