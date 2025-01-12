import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { QuestionComponent } from './pages/question/question.component';
import { CreateQuestionComponent } from './pages/question/create-question/create-question.component';
import { QuestionDetailComponent } from './pages/question/question-detail/question-detail.component';
import { ProfileUpdateComponent } from './pages/profile-update/profile-update.component';
import { MessageComponent } from './pages/message/message.component';
import { UserGuard } from './guard/user.guard';
import { ReportedComponent } from './pages/reported/reported.component';
import { AdminGuard } from './guard/admin.guard';
import { NotificationComponent } from './pages/notification/notification.component';

const routes: Routes = [
  {
    path: "",
    pathMatch: "full",
    redirectTo: "/questions"
  },
  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "register",
    component: RegisterComponent
  },
  { 
    path: 'questions',
    component: QuestionComponent,
    data: { scenario: 'all' }
  },
  { 
    path: 'your-questions',
    component: QuestionComponent,
    data: { scenario: 'owner' },
    canActivate: [UserGuard]
  },
  { 
    path: 'questions/:mainCategory/:subCategory',
    component: QuestionComponent,
    data: { scenario: 'category' }
  },
  {
    path: 'create-question',
    component: CreateQuestionComponent,
    canActivate: [UserGuard]
  },
  {
    path: 'question/:id/details',
    component: QuestionDetailComponent
  },
  {
    path: 'update-user',
    component: ProfileUpdateComponent,
    canActivate: [UserGuard]
  },
  {
    path: 'messages',
    component: MessageComponent,
    canActivate: [UserGuard]
  },
  {
    path: 'notifications',
    component: NotificationComponent,
    canActivate: [UserGuard]
  },
  {
    path: 'reported',
    component: ReportedComponent,
    canActivate: [AdminGuard]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
