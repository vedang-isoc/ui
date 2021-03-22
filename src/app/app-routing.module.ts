import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminhomeComponent } from './admin/adminhome/adminhome.component';
import { AuthGuard } from './auth.guard';
import { ErrorComponent } from './error/error.component';
import { ForgotPasswordComponent } from './shared/forgot-password/forgot-password.component';
import { IndexComponent } from './shared/index/index.component';

import { RegisterComponent } from './shared/register/register.component';
import { CourseDetailComponent } from './usermodule/course-detail/course-detail.component';
import { CoursesComponent } from './usermodule/courses/courses.component';
import { CreateprofileComponent } from './usermodule/createprofile/createprofile.component';
import { MycoursesComponent } from './usermodule/mycourses/mycourses.component';
import { UserhomeComponent } from './usermodule/userhome/userhome.component';
import { VerifyComponent } from './usermodule/verify/verify.component';
import { VideoDisplayComponent } from './usermodule/video-display/video-display.component';

const routes: Routes = [

  { path: "", component: IndexComponent },
  { path: "register", component: RegisterComponent },
  { path: "createprofile", component: CreateprofileComponent },
  {
    path: 'verify', component: VerifyComponent,
    data: { role: "ROLE_USER" }
  },
  {
    path: "user", component: UserhomeComponent,
    data: { role: "ROLE_USER" },
    canActivate:[AuthGuard]
  },
  { path: "admin", component: AdminhomeComponent, data: { role: "ROLE_ADMIN" } },
  {
    path: "forgot", component: ForgotPasswordComponent,
    data: { role: "ROLE_USER" }
  },
  {
    path: 'coursedetail', component: CourseDetailComponent,
    data: { role: "ROLE_USER" }
  },
  {
    path: 'videolist', component: VideoDisplayComponent,
    data: { role: "ROLE_USER" }
  },
  {
    path: 'courses', component: MycoursesComponent,
    data: { role: "ROLE_USER" }
  }


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
