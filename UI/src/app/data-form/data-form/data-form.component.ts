import * as _  from 'lodash';
import { City } from './../../models/City';
import { State } from './../../models/State';
import { Country } from './../../models/Country';
import { GlobalService } from './../../services/global.service';
import { NavigationService } from 'src/app/navigation.service';
import { ImageDetails } from '../../models/image-details';
import { DataService } from '../data.service';
import { AppMessageService, MESSAGE_TYPE } from 'src/app/message/message.service';
import { Component, OnInit } from '@angular/core';
import imageCompression from 'browser-image-compression';

@Component({
  selector: 'app-data-form',
  templateUrl: './data-form.component.html',
  styleUrls: ['./data-form.component.css']
})
export class DataFormComponent implements OnInit {

  imageDetails: ImageDetails = new ImageDetails();
  selectedFiles: any[] = [];
  uploadedFiles: any[] = [];
  fileDataStore:any[] = [];
  originalFile = null;
  compressedFile = null;
  compressedFileUrl = null;
  show = {
    uploadFile: false
  };

  countryList: Country[] = [];
  stateList: State[] = [];
  cityList: City[] = [];

  baseCountryList: Country[] = [];
  baseStateList: State[] = [];
  baseCityList: City[] = [];

  clearForm() {
    this.imageDetails = new ImageDetails();
    this.selectedFiles = [];
    this.uploadedFiles = [];
    this.fileDataStore = [];
    this.originalFile = null;
    this.compressedFile = null;
    this.compressedFileUrl = null;
  }

  constructor(private appMessageService: AppMessageService,
    private navigationService: NavigationService,
    private globalService: GlobalService,
    private dataService: DataService) { }

  ngOnInit() {
    this.countryList = _.cloneDeep(this.globalService.getCountryList());
    this.stateList = _.cloneDeep(this.globalService.getStateList());
    this.cityList =_.cloneDeep((this.globalService.getCityList()));

    this.baseCountryList = _.cloneDeep(this.globalService.getCountryList());
    this.baseStateList = _.cloneDeep(this.globalService.getStateList());
    this.baseCityList = _.cloneDeep(this.globalService.getCityList());

    
    this.imageDetails.country = this.countryList.find(country => country.phonecode === 91);
    this.imageDetails.state = this.stateList.find(state => state.name.toLocaleLowerCase() === "maharashtra");
    this.imageDetails.city = this.cityList.find(city => city.name.toLocaleLowerCase() === "nashik");

    this.imageDetails.country = this.baseCountryList.find(country => country.phonecode === 91);
    this.imageDetails.state = this.baseStateList.find(state => state.name.toLocaleLowerCase() === "maharashtra");
    this.imageDetails.city = this.baseCityList.find(city => city.name.toLocaleLowerCase() === "nashik");
  }

  fileSelectHandler(event) {
    for(let file of event.files) {
      let fileName = file.name;
      const extension = fileName.substr(fileName.lastIndexOf('.')+1, fileName.length);
      let allow_file = ['exe','dll'];
      if(allow_file.indexOf(extension) !== 1) {
        this.selectedFiles.push(file);
      }else{
        this.appMessageService.showMessage(MESSAGE_TYPE.ERROR,"Please select valid file type.");
        return false;
      }
    }
    this.fileUploadHandler(event);
  }


  fileUploadHandler(event) {
    const me = this;
    for(let file of event.files) {
      var reader = new FileReader();
      reader.onload = this.handleReaderLoaded.bind(this);
      reader.readAsBinaryString(file);
      this.uploadedFiles.push(file);
      var options = { maxSizeMB: 0.3, maxWidthOrHeight: 2048, useWebWorker: true }
      imageCompression(file, options).then(function (compressedFile) {
        console.log('compressedFile instanceof Blob', compressedFile instanceof Blob); // true
        console.log(`compressedFile size ${compressedFile.size / 1024 / 1024} MB`); // smaller than maxSizeMB
        me.getBase64(compressedFile);
        me.compressedFile = compressedFile;
        me.originalFile = file;
      });
    }
  }

  getBase64(file) {
    const me = this;
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function () {
      console.log(reader.result);
      me.compressedFileUrl = reader.result;
    };
    reader.onerror = function (error) {
      console.log('Error: ', error);
    };
 }

  toBase64 = file => new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
});

  handleReaderLoaded(readerEvt) {
    this.show.uploadFile = true;
    var binaryString = readerEvt.target.result;
    const binarydata = btoa(binaryString);
    this.fileDataStore.push(binarydata);
  }


  onUpload() {
    const body = new FormData();
    body.append('file', this.compressedFile);
    const details = _.cloneDeep(this.imageDetails);

    this.setAddressDetailsBeforeSave(details);

    details['imageName'] = this.originalFile.name;
    body.append('details', JSON.stringify(details));
    this.dataService.uploadFile(body).subscribe(
      response => {
        if (response['status']) {
          this.appMessageService.showMessage(MESSAGE_TYPE.SUCCESS, response['message']);
          this.clearForm();
        } else {
          this.appMessageService.showMessage(MESSAGE_TYPE.ERROR, response['message']);
        }
      }
    )
  }

  setAddressDetailsBeforeSave(details) {
    details.country = this.imageDetails.country.id;
    details.state = this.imageDetails.state.id;
    details.city = this.imageDetails.city.id;
    details.baseCountry = this.imageDetails.baseCountry.id;
    details.baseState = this.imageDetails.baseState.id;
    details.baseCity = this.imageDetails.baseCity.id;
  }

  onBack($event) {
    this.navigationService.openDataListPage();
  }

  onCountryChange(event, idx) {
    const countryId  = event.value.id;
    const masterStateList = this.globalService.getStateList();
    const filteredList = masterStateList.filter(state => state.countryId === countryId);
    if (idx) {
      this.baseStateList = filteredList;
    } else {
      this.stateList = filteredList;
    }
  }

  onStateChange(event, idx) {
    const stateId = event.value.id;
    const masterCityList = this.globalService.getCityList();
    const filteredList = masterCityList.filter(city => city.stateId === stateId);
    if (idx) {
      this.baseCityList = filteredList;
    } else {
      this.cityList = filteredList;
    }
  }
}
