import { NavigationService } from 'src/app/navigation.service';
import { urls } from 'src/app/constants/Constants';
import { GridColumn } from './../../app-grid/grid-column';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-data-list',
  templateUrl: './data-list.component.html',
  styleUrls: ['./data-list.component.scss']
})
export class DataListComponent implements OnInit {

  imageListColumns: GridColumn[];
  imageListGridPageSize = 20;
  imageListFilters = [];
  imageListDataSourceURL = urls.IMAGE.GET;
  constructor(private navigationService: NavigationService) { }

  ngOnInit() {

    this.imageListColumns = [
      { field: 'nookh', header: 'Nookh', sortable: true, filter: true, customColumn: { type: 'link', serverCol: 'nookh'}},
      { field: 'firstName', header: 'First name', sortable: true, filter: true, customColumn: { type: 'link', serverCol: 'firstName'}},
      { field: 'lastName', header: 'Last name', sortable: true, filter: true, customColumn: { type: 'text', serverCol: 'lastName'}},

      { field: 'parentName', header: 'Parent name', sortable: true, filter: true, customColumn: { type: 'text', serverCol: 'parentName'}},
      { field: 'parentName1', header: 'Grand parent name', sortable: true, filter: true, customColumn: { type: 'text', serverCol: 'parentName1'}},
      { field: 'parentName2', header: 'Great grand parent name', sortable: true, filter: true, customColumn: { type: 'text', serverCol: 'parentName2'}},

      { field: 'mobile', header: 'Mobile', sortable: true, filter: true, customColumn: { type: 'text', serverCol: 'mobile'}},

      { field: 'baseAddress', header: 'Base address', sortable: true, filter: true, customColumn: { type: 'text', serverCol: 'baseAddress'}},
      { field: 'baseCountry', header: 'Base country', sortable: true, filter: true, customColumn: { type: 'text', serverCol: 'baseCountry'}},
      { field: 'baseState', header: 'Base State', sortable: true, filter: true, customColumn: { type: 'text', serverCol: 'baseState'}},
      { field: 'baseCity', header: 'Base city', sortable: true, filter: true, customColumn: { type: 'text', serverCol: 'baseCity'}},

      { field: 'currentAddress', header: 'Current address', sortable: true, filter: true, customColumn: { type: 'text', serverCol: 'currentAddress'}},
      { field: 'country', header: 'Country', sortable: true, filter: true, customColumn: { type: 'text', serverCol: 'country'}},
      { field: 'state', header: 'State', sortable: true, filter: true, customColumn: { type: 'text', serverCol: 'state'}},
      { field: 'city', header: 'City', sortable: true, filter: true, customColumn: { type: 'text', serverCol: 'city'}}
      // { field: 'pincode', header: 'Pincode', sortable: true, filter: true, customColumn: { type: 'text', serverCol: 'pincode'}},
      
    ];
  }


  onAddImage(e) {
    this.navigationService.openDataFormPage();
  }
}
