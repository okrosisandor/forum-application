import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClient, HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { FormsModule } from '@angular/forms';
import { MenuComponent } from './components/menu/menu.component';
import { CategoriesComponent } from './components/categories/categories.component';
import { QuestionComponent } from './pages/question/question.component';
import { UserMenuComponent } from './components/user-menu/user-menu.component';
import { CreateQuestionComponent } from './pages/question/create-question/create-question.component';
import { QuestionDetailComponent } from './pages/question/question-detail/question-detail.component';
import { AuthOptionsComponent } from './components/auth-options/auth-options.component';
import { PaginationComponent } from './common/pagination/pagination.component';
import { HttpTokenInterceptor } from './interceptor/token-interceptor.interceptor';
import { ProfileUpdateComponent } from './pages/profile-update/profile-update.component';
import { MessageComponent } from './pages/message/message.component';
import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReplaceUnderscorePipe } from './pipe/replace-underscore.pipe';
import { AdminMenuComponent } from './components/admin-menu/admin-menu.component';
import { ReportedComponent } from './pages/reported/reported.component';
import { NotificationComponent } from './pages/notification/notification.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    MenuComponent,
    CategoriesComponent,
    QuestionComponent,
    UserMenuComponent,
    CreateQuestionComponent,
    QuestionDetailComponent,
    AuthOptionsComponent,
    PaginationComponent,
    ProfileUpdateComponent,
    MessageComponent,
    ReplaceUnderscorePipe,
    AdminMenuComponent,
    ReportedComponent,
    NotificationComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ToastrModule.forRoot({
      progressBar: true,
      closeButton: true,
      newestOnTop: true,
      tapToDismiss: true,
      positionClass: "toast-bottom-right",
      timeOut: 4000
    })
  ],
  providers: [
    HttpClient,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpTokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
