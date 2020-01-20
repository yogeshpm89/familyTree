import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/components/common/menuitem';
import { LabelService } from 'src/app/services/label.service';
import { NavigationService } from 'src/app/navigation.service';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.scss']
})
export class AdminHomeComponent implements OnInit {

  menuList: MenuItem[];
  activeItem: MenuItem;
  lables;

  constructor(
    private labelService: LabelService,
    private navigation: NavigationService) { 
    
  }



  ngOnInit() {
    const tabMenuClick = function(a,c,b) {
    }
    this.lables = this.labelService.getLabels();
    this.menuList = [
      { label: this.lables.adminTabs.users, command: () => tabMenuClick},
      { label: this.lables.adminTabs.config, command: () => tabMenuClick}
    ];

     this.activeItem = this.menuList[0];
     this.navigation.openAdminUserManagementPage();
  }

}
