import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { formatDate } from 'src/app/common/utilities';
import { GlobalMessageRequest } from 'src/app/model/global-message-request';
import { MessageRequest } from 'src/app/model/message-request.model';
import { MessageResponse } from 'src/app/model/message-response.model';
import { PageResponse } from 'src/app/model/page-response.model';
import { AdminService } from 'src/app/services/admin.service';
import { MessageService } from 'src/app/services/message.service';
import { TokenService } from 'src/app/services/token.service';
import { WebSocketService } from 'src/app/services/websocket.service';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements OnInit {

  @ViewChild('scrollableContent') scrollableContent!: ElementRef;

  incomingMessagesPage!: PageResponse<MessageResponse>;
  outgoingMessagesPage!: PageResponse<MessageResponse>;
  currentIncomingPage: number = 0;
  currentOutgoingPage: number = 0;
  pageSize = 10;
  userId: number = this.tokenService.user.userId;

  sendTypes = [
    { label: 'To Certain User', value: 'TO_USER' },
    { label: 'To All Users', value: 'TO_ALL' }
  ];
  
  sendType: string = this.isAdmin? "" : this.sendTypes[0].value;

  message: MessageRequest = {
    message: "",
    from: this.tokenService.user.displayName,
    receiverUserName: ""
  };

  globalMessage: GlobalMessageRequest = {
    message: "",
    from: this.tokenService.user.displayName,
  };

  errorMsg: Array<string> = [];
  activeTab: string = 'incoming';

  constructor(
    private messageService: MessageService,
    private tokenService: TokenService,
    private toastService: ToastrService,
    private adminService: AdminService,
    private webSocketService: WebSocketService
  ) {}

  ngOnInit(): void {
    this.webSocketService.resetUnreadMessages();
    this.getMessagesByReceiver();
  }

  switchTab(tab: string): void {
    this.activeTab = tab;

    if (tab === 'incoming') {
      this.getMessagesByReceiver();
      this.currentOutgoingPage = 0;
    } else if (tab === 'outgoing') {
      this.getMessagesBySender();
      this.currentIncomingPage = 0;
    }
  }

  getMessagesByReceiver(): void {
    this.messageService.getMessagesByReceiver(this.userId, this.currentIncomingPage, this.pageSize).subscribe({
      next: (response: PageResponse<MessageResponse>) => {
        this.incomingMessagesPage = response;
        this.scrollToTop();
      },
      error: (error) => {
        console.error('Failed to fetch messages:', error);
      }
    });
  }

  getMessagesBySender(): void {
    this.messageService.getMessagesBySender(this.userId, this.currentOutgoingPage, this.pageSize).subscribe({
      next: (response: PageResponse<MessageResponse>) => {
        this.outgoingMessagesPage = response;
        this.scrollToTop();
      },
      error: (error) => {
        console.error('Failed to fetch messages:', error);
      }
    });
  }

  formattedDate(date: string): string {
    return formatDate(date);
  }

  createMessage(): void {
    this.errorMsg = [];

    if(this.sendType === 'TO_USER') {
      this.messageService.createMessage(this.message).subscribe({
        next: () => {
        this.message.message = '';
        this.message.receiverUserName = '';
        this.toastService.info("Message has been sent", "Success");
        },
        error: (error) => {
          if (error.error.validationErrors) {
            this.errorMsg = error.error.validationErrors;
          } else{
            this.errorMsg.push(error.error.message)
          }
        }
      });
    }

    if(this.sendType === 'TO_ALL') {
      this.adminService.createGlobalMessage(this.globalMessage).subscribe({
        next: () => {
        this.globalMessage.message = '';
        this.toastService.info("Global message has been sent", "Success");
        },
        error: (error) => {
          if (error.error.validationErrors) {
            this.errorMsg = error.error.validationErrors;
          } else{
            this.errorMsg.push(error.error.message)
          }
        }
      });
    }
  }

  get isAdmin(): boolean {
    return this.tokenService.hasAuthority("ROLE_ADMIN");
  }

  scrollToTop(): void {
    if (this.scrollableContent) {
      this.scrollableContent.nativeElement.scrollIntoView({
        behavior: 'smooth',
        block: 'start'
      });
    }
  }

  onPageChanged(page: number): void {
    if (this.activeTab === 'incoming') {
      this.currentIncomingPage = page;
      this.getMessagesByReceiver();
    } else if (this.activeTab === 'outgoing') {
      this.currentOutgoingPage = page;
      this.getMessagesBySender();
    }
  }
}

