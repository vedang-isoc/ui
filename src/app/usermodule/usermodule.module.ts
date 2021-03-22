import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserhomeComponent } from './userhome/userhome.component';
import {MatButtonModule} from '@angular/material/button';
import { VerifyComponent } from './verify/verify.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AlertmodelComponent } from './alertmodel/alertmodel.component';
import { CoursesComponent } from './courses/courses.component';
import { MycoursesComponent } from './mycourses/mycourses.component';
import { UpdatecommentComponent } from './updatecomment/updatecomment.component';
import { VideoDisplayComponent } from './video-display/video-display.component';
import { NgxStarRatingModule } from 'ngx-star-rating';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import { MatDialogModule } from '@angular/material/dialog';
import {MatTabsModule} from '@angular/material/tabs';
import { CourseDetailComponent } from './course-detail/course-detail.component';
import { NgxInputStarRatingModule } from 'ngx-input-star-rating';
import { CreateprofileComponent } from './createprofile/createprofile.component';
import { SafePipe } from '../safe.pipe';


@NgModule({
  declarations: [UserhomeComponent, VerifyComponent, AlertmodelComponent, CoursesComponent, MycoursesComponent, UpdatecommentComponent, VideoDisplayComponent, CourseDetailComponent, CreateprofileComponent,SafePipe],
  imports: [
    CommonModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    NgxStarRatingModule,
    MatFormFieldModule,
    MatInputModule,
    MatDialogModule,
    MatTabsModule,
    NgxInputStarRatingModule,
  ],
  exports:[
  ]
})
export class UsermoduleModule { }
