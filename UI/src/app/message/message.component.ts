import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/components/common/messageservice';
import { AppMessageService, Message } from './message.service';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.scss'],
  providers: [MessageService]
})
export class MessageComponent implements OnInit {

  constructor(private appMessageService: AppMessageService,
    private messageService: MessageService) {
    this.appMessageService.showMessageSource$.subscribe(
      message => {
        this.messageService.add(message as Message);
      }
    )
  };


  ngOnInit() {
  }

}
