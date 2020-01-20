import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { AdminRoutesModule } from './admin-routes/admin-routes.module';
import { TabMenuModule } from 'primeng/tabmenu';
import { UserModule } from '../user/user.module';


@NgModule({
  declarations: [AdminHomeComponent],
  imports: [
    CommonModule,
    AdminRoutesModule,
    TabMenuModule,
    UserModule
  ]
})
export class AdminModule { }
