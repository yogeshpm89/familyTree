import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListComponent } from './list/list.component';
import { AppGridModule } from '../app-grid/app-grid.module';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import { FormComponent } from './form/form.component';
import {DialogModule} from 'primeng/dialog';
import {InputSwitchModule} from 'primeng/inputswitch';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [ListComponent, FormComponent],
  imports: [
    FormsModule,
    CommonModule,
    AppGridModule,
    ConfirmDialogModule,
    DialogModule,
    InputSwitchModule
  ],
  exports: [
    ListComponent
  ]
})
export class UserModule { }
