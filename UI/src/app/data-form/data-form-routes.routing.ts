import { DataHomeComponent } from './data-home/data-home.component';
import { DataListComponent } from './data-list/data-list.component';
import { DataFormComponent } from './data-form/data-form.component';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    component: DataHomeComponent,
    children: [
      { path: 'list', component: DataListComponent },
      { path: 'form', component: DataFormComponent },
      
    ]
  }
];
export const DataFormRoutesRoutes = RouterModule.forChild(routes);
