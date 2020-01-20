import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { GridComponent } from './grid/grid.component';

@NgModule({
  imports: [
    CommonModule,
    TableModule
  ],
  exports: [
    GridComponent
  ],
  declarations: [GridComponent]
})
export class AppGridModule { }
