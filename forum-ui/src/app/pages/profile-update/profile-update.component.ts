import { Component } from '@angular/core';
import { UserResponse } from 'src/app/model/user/user-response';
import { UserUpdateRequest } from 'src/app/model/user/user-update-request.model';
import { TokenService } from 'src/app/services/token.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile-update',
  templateUrl: './profile-update.component.html',
  styleUrls: ['./profile-update.component.css']
})
export class ProfileUpdateComponent {
  userId: number = this.tokenService.user.userId;
  showSuccessMessage: boolean = false;

  updateUserRequest: UserUpdateRequest = {
    email: null,
    displayName: null,
    dateOfBirth: null,
    password: null,
  };

  errorMsg: Array<string> = [];
  successMsg: string = "Update successful";

  constructor(
    private userService: UserService,
    private tokenService: TokenService
  ) {}

  updateUser() {
    this.errorMsg = [];
    this.showSuccessMessage = false;

    if (this.updateUserRequest.email && !this.isValidEmail(this.updateUserRequest.email)) {
      this.errorMsg.push('Invalid email address');
    }

    if (this.updateUserRequest.password && this.updateUserRequest.password.length < 6) {
      this.errorMsg.push('Password should be minimum 6 characters');
    }

    if (this.errorMsg.length > 0) {
      return;
    }

    this.userService.updateUser(this.updateUserRequest, this.userId).subscribe({
      next: (response: UserResponse) => {
        this.tokenService.user = response;
        this.showSuccessMessage = true;
        this.updateUserRequest = {};
      },
      error: (error) => {
      },
    });
  }

  isValidEmail(email: string): boolean {
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    return emailPattern.test(email);
  }

  isFormEmpty(): boolean {
    return !(
      this.updateUserRequest.displayName ||
      this.updateUserRequest.email ||
      this.updateUserRequest.dateOfBirth ||
      this.updateUserRequest.password
    );
  }
}
