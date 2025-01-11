import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationRequest } from 'src/app/model/user/authentication-request.model';
import { AuthenticationResponse } from 'src/app/model/user/authentication-response.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { TokenService } from 'src/app/services/token.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  returnUrl: string = '';
  authRequest: AuthenticationRequest = {email: "", password: ""};
  errorMsg: Array<string> = [];

  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private tokenService: TokenService,
    private route: ActivatedRoute,
  ) { }

  ngOnInit() {
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/questions';
  }

  login() {
    this.errorMsg = [];

    this.authService.authenticate(this.authRequest).subscribe({
      next: (response: AuthenticationResponse) => {
        this.tokenService.token = response.token as string;
        this.tokenService.user = response.user;
        
        this.router.navigateByUrl(this.returnUrl);
      },
      error: (error) => {
        if (error.error.validationErrors) {
          this.errorMsg = error.error.validationErrors;
        } else {
          this.errorMsg.push(error.error.message)
        }
      },
    });
  }

  register(){
    this.router.navigate(['register']);
  }

}
