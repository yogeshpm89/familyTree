import { DataService } from './../data.service';
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

  showImage = false;
  selectedImage = null;
  selectedRow = null;

  imageListColumns: GridColumn[];
  imageListGridPageSize = 20;
  imageListFilters = [];
  imageListDataSourceURL = urls.IMAGE.GET;
  constructor(private navigationService: NavigationService,
    private dataService: DataService) { }

  ngOnInit() {
    
    const onNameClick = (row) => {
      this.selectedRow = row;
      this.getImage(row.imageName);
    }

    this.imageListColumns = [
      
      { field: 'firstName', header: 'First name', sortable: true, filter: true, customColumn: { type: 'link', serverCol: 'firstName', clickFn: onNameClick}},
      { field: 'lastName', header: 'Last name', sortable: true, filter: true, customColumn: { type: 'text', serverCol: 'lastName'}},
      { field: 'nookh', header: 'Nookh', sortable: true, filter: true, customColumn: { type: 'text', serverCol: 'nookh'}},
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

  getImage(imageName: string) {
    this.dataService.getImage(imageName).subscribe(
      response => {
        this.showImage = true;
        this.selectedImage = "data:image/png;base64," + response['data'];
      }
    )
  }
}
