import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { UserService } from '../user.service';
import { AppMessageService, MESSAGE_TYPE } from 'src/app/message/message.service';
import { User, UserType } from 'src/app/models/user';

@Component({
  selector: 'app-user-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {

  @Input() user: User;
  @Output() onDone = new EventEmitter();
  isAdmin: boolean = false;
  isAdd: boolean = false;
  disabled = {
    displayName: false,
    email: false,
    address: false,
    phone: false,
    isAdmin: false
  }
  constructor(private userService: UserService,
    private appMessageService: AppMessageService) { }

  ngOnInit() {
    if (!this.user) {
      this.user = new User();
      this.isAdd = true;
    } else {
      this.disabled.email = true;
    }
    this.isAdmin = (this.user.userRole === UserType.ADMIN);
  }

  onSave() {
    if (this.isAdmin) {
      this.user.userRole = UserType.ADMIN;
    } else {
      this.user.userRole = UserType.USER;
    }
    this.userService.saveUser(this.user).subscribe(
      data => {
        if (data['status']) {
          this.appMessageService.showMessage(MESSAGE_TYPE.SUCCESS, '');
          this.onDone.emit();
        } else {
          this.appMessageService.showMessage(MESSAGE_TYPE.ERROR, '');
        }
      }
    )
  }

}
