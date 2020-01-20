import { Component, OnInit, ViewChild } from '@angular/core';
import { GridColumn } from 'src/app/app-grid/grid-column';
import { User } from 'src/app/models/user';
import { GridComponent } from 'src/app/app-grid/grid/grid.component';
import { UserService } from '../user.service';
import { urls } from 'src/app/constants/Constants';
import { ConfirmationService } from 'primeng/components/common/confirmationservice';
import { AppMessageService } from 'src/app/message/message.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss'],
  providers: [ConfirmationService]
})

export class ListComponent implements OnInit {

  showUserForm = false;
  userListColumns: GridColumn[];
  userListGridPageSize = 20;
  userListFilters = [];
  userListDataSourceURL = urls.USER.GET;
  selectedUser: User;

  @ViewChild(GridComponent, {static: false})
  private userGrid: GridComponent;
  
  constructor(private userService: UserService,
    private confirmationService: ConfirmationService,
    private appMessageService: AppMessageService) { }

  ngOnInit() {
    const me = this;
    const onEmailClick = (row) => {
      this.selectedUser = row;
      this.showUserForm = true;
    }

    const deleteClick = (row) => {
    //   me.confirmationService.confirm({
    //     message: 'Are you sure that you want to delete user [' + row.email + '] ?',
    //     header: 'Delete confirmation',
    //     icon: 'fa fa-exclamation-triangle fa-2x',
    //     accept: () => {
    //         me.onDeleteUser(row.userId);
    //     }
    // });
    }

    this.userListColumns = [
      { field: 'email', header: 'Email address', sortable: true, filter: true, customColumn: { type: 'link', serverCol: 'email', clickFn: onEmailClick}},
      { field: 'displayName', header: 'Display name', sortable: true, filter: true, customColumn: { type: 'text', serverCol: 'displayName' }},
      { field: 'address', header: 'Address', sortable: true, filter: true, customColumn: { type: 'text', serverCol: 'address' }},
      { field: 'phone', header: 'Phone', sortable: true, filter: true, customColumn: { type: 'text', serverCol: 'phone' }},
      { field: 'role', header: 'Role', sortable: true, filter: true, customColumn: { type: 'text', serverCol: 'userRole' }},
      { field: 'isActive', header: 'Action', sortable: true, filter: false, customColumn: { type: 'Bttnlink', clickFn: deleteClick}},
    ];

    // this.userListFilters.push({
    //   'FILTER_KEY':'isActive',
    //   'FILTER_VALUE': 1,
    //   'FILTER_TYPE': REQUEST.FILTER_TYPE_NUMBER
    // });
  }

  onAddUser(e) {
    this.selectedUser = null;
    this.showUserForm = true;
  }

  onDeleteUser(userId) {
    this.userService.deleteUser(userId).subscribe(
      response => {
        if (response['status']) {
          // this.appMessageService.showMessage(MESSAGE_TYPE.SUCCESS, response['message']);
          this.userGrid.refresh();
        } else {
          // this.appMessageService.showMessage(MESSAGE_TYPE.ERROR, response['message'])
        }
      }
    )
  }

  onUndeleteUser(userId) {
    this.userService.undeleteUser(userId).subscribe(
      response => {
        if (response['status']) {
          // this.appMessageService.showMessage(MESSAGE_TYPE.SUCCESS, response['message']);
          this.userGrid.refresh();
        } else {
          // this.appMessageService.showMessage(MESSAGE_TYPE.ERROR, response['message']);
        }
      }
    )
  }

  onUserFormDone() {
    this.selectedUser = null;
    this.showUserForm = false;
    this.userGrid.refresh();
  }
}
