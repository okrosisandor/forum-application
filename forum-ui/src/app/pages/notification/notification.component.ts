import { Component, ElementRef, ViewChild } from '@angular/core';
import { formatDate } from 'src/app/common/utilities';
import { GlobalMessageResponse } from 'src/app/model/global-message-response';
import { PageResponse } from 'src/app/model/page-response.model';
import { NotificationService } from 'src/app/services/notification.service';
import { TokenService } from 'src/app/services/token.service';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css'],
})
export class NotificationComponent {

  @ViewChild('scrollableContent') scrollableContent!: ElementRef;

  messagesPage!: PageResponse<GlobalMessageResponse>;
  currentPage: number = 0;
  pageSize = 10;
  userId: number = this.tokenService.user.userId;

  constructor(
    private notificationService: NotificationService,
    private tokenService: TokenService,
  ) {}

  ngOnInit(): void {
    this.getMessagesBySender();
  }

  getMessagesBySender(): void {
    this.notificationService
      .getMessagesBySender(this.currentPage, this.pageSize)
      .subscribe({
        next: (response: PageResponse<GlobalMessageResponse>) => {
          this.messagesPage = response;
          this.scrollToTop();
        },
        error: (error) => {
          console.error('Failed to fetch messages:', error);
        },
      });
  }

  formattedDate(date: string): string {
    return formatDate(date);
  }

  get isAdmin(): boolean {
    return this.tokenService.hasAuthority('ROLE_ADMIN');
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
    this.currentPage = page;
    this.getMessagesBySender();
  }
}
