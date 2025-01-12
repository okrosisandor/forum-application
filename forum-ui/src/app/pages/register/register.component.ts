import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { RegistrationRequest } from 'src/app/model/registration-request.model';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  registerRequest: RegistrationRequest = {
    email: '',
    displayName: '',
    dateOfBirth: null,
    password: '',
  };
  errorMsg: Array<string> = [];

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {}

  register() {
    this.errorMsg = [];
  
    this.authService.register(this.registerRequest).subscribe({
      next: (response) => {
        this.router.navigate(['login']);
      },
      error: (error) => {
        if (error.status === 409) {
          const backendMessage = error.error.message;
          if (backendMessage.includes('display_name')) {
            this.errorMsg = ['The username is already taken. Please choose another.'];
          } else if (backendMessage.includes('email')) {
            this.errorMsg = ['A user with this email already exists.'];
          } else {
            this.errorMsg = [backendMessage];
          }
        } else {
            this.errorMsg = error.error.validationErrors;
        }
      },
    });
  }

  login() {
    this.router.navigate(['login']);
  }
}
