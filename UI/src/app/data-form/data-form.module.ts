import { AppGridModule } from './../app-grid/app-grid.module';
import { DataHomeComponent } from './data-home/data-home.component';
import { DataListComponent } from './data-list/data-list.component';
import { DataFormRoutesRoutes } from './data-form-routes.routing';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DataFormComponent } from './data-form/data-form.component';
import {FileUploadModule} from 'primeng/fileupload';
import { FormsModule } from '@angular/forms';
import {DropdownModule} from 'primeng/dropdown';
import {InputTextareaModule} from 'primeng/inputtextarea';
import {FieldsetModule} from 'primeng/fieldset';
import {AccordionModule} from 'primeng/accordion';

@NgModule({
  imports: [
    CommonModule,
    FieldsetModule,
    AccordionModule,
    FormsModule,
    DropdownModule,
    InputTextareaModule,
    DataFormRoutesRoutes,
    FileUploadModule,
    AppGridModule,
    
  ],
  declarations: [DataFormComponent, DataListComponent, DataHomeComponent]
})
export class DataFormModule { }
