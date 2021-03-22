import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticateService } from 'src/app/authenticate.service';
import { UserService } from 'src/app/user.service';
import { AlertmodelComponent } from '../alertmodel/alertmodel.component';
import { UpdatecommentComponent } from '../updatecomment/updatecomment.component';

@Component({
  selector: 'app-course-detail',
  templateUrl: './course-detail.component.html',
  styleUrls: ['./course-detail.component.scss']
})
export class CourseDetailComponent implements OnInit {

  form: FormGroup;
  feedbackcount: number;
  commentcount: number;
  courses: any;
  videos: any;
  value: Observable<number>;
  feedbacks: any;
  comments: any
  rating3: number;
  addComment: FormGroup
  courseid: number;
  islike: any;
  isenrolled: any;
  isProfileCreated:any;
  isFinished:any;
  username= sessionStorage.getItem("username")
  buttoncomment : boolean[] = []

  // index=0
  userid = Number(sessionStorage.getItem("userId"))
  constructor(private formBuilder: FormBuilder, private us: UserService, private ar: ActivatedRoute, private router: Router, private as: AuthenticateService, 
   private formbuilder:FormBuilder,public dialog: MatDialog) {
  }

  ngOnInit(): void {

    //courseid fetch value
    this.ar.queryParams.subscribe((p) => {
      this.courseid = Number(atob(p['courseid']))
    })

    //get likes
    this.us.getlike(this.courseid, this.userid).subscribe((data) => {
      this.islike = data
    
    })
    this.us.isProfileCreated(this.userid).subscribe((x)=>{
      this.isProfileCreated=true
    })

   

    this.us.completedcourse(this.courseid, this.userid)
    .subscribe((data) => {
      this.isFinished = data
      
      
     
    })


    //get enrollement
    this.us.getEnrollement(this.courseid, this.userid).subscribe((data) => {
      this.isenrolled = data
      
    })

    //get course by id
    this.us.getCoursById(this.courseid).subscribe((data) => {

      this.courses = data
    })

    //get total number of feedbacks
    this.us.getFeedbackcount(this.courseid).subscribe((data) => {
      this.feedbackcount = data
    })

    //get total number of comments
    this.us.getCommentcount(this.courseid).subscribe((data) => {
      this.commentcount = data
    })

    this.form = this.formBuilder.group({
      rating: [3],
      feedback: new FormControl("")
    });

    this.value = this.form.controls.rating.valueChanges;

    //get comments
    this.us.getComment(this.courseid).subscribe((data) => {
      this.comments = data
     
      
    })

   
     
    

    //get videos
    this.us.getVideos(this.courseid).subscribe((data) => {
      this.videos = data
    })

    //get feedbacks
    this.us.getFeedback(this.courseid).subscribe((data) => {
      this.feedbacks = data
    })

    //add comments
    this.addComment = new FormGroup({
      // userId: new FormControl(this.addComment.value.userId),
      // courseId: new FormControl(this.addComment.value.courseId),
      comment: new FormControl(""),
    })


   



  }

  

  submit() {
    console.log(this.form.value);
  }

  addCom(addComment) {
    console.log(this.courseid)
    this.us.addComment(this.userid, this.courseid, addComment.value.comment)
      .subscribe({
        next: () => {
          console.log("comment")
        }
      })
  }

  addFed() {
    console.log(this.form.value.rating)
    this.us.addFeedback(this.userid, this.courseid, this.form.value.feedback, this.form.value.rating)
      .subscribe({
        next: () => {
          console.log("comment")
          this.router.navigate(['/coursedetail'], { queryParams: { courseid: btoa(String(this.courseid)) } })
        }
      })

  }

  //add like
  addlike() {

    console.log(this.islike)
    if (!this.islike) {
      console.log("if")
      this.us.addlikes(this.courseid, this.userid)
        .subscribe({
          next: () => {
            console.log("liked")
            this.islike = true
          },
          error: () => {
            console.log("liked")
            this.islike = true
          }
        })
    }
    if (this.islike) {
      console.log("else")
      this.us.removelikes(this.courseid, this.userid)
        .subscribe({
          next: () => {
            console.log("unliked")
          }
        })
    }
  }


  //add enrollement
  enroll() {
    if(this.isProfileCreated){
      this.us.addEnrollement(this.courseid, this.userid)
      .subscribe({
        next: () =>{
          console.log("enrolled")
        }
      })

    }
    else{
      
      this.dialog.open(AlertmodelComponent,{
        data: {value:"you need to create profile first"}
      });
    }
   
    
  }

  //go to video list
  video(courseid){
    this.router.navigate(['/videolist'],{queryParams:{courseid:btoa(courseid)}})
  }


  //deletecomment
  deletecomment(commentid){
    this.us.deletecomment(commentid).subscribe({
      next:()=>{
        console.log('deleted')
      }
    })
    
  }


  //updatecomment
  updatecomment(commentid,comment_msg){
    const dialogRef = this.dialog.open(UpdatecommentComponent, {
      width: '250px',
      data: {
        courid : this.courseid,
        useid : this.userid,
        comm : commentid, 
        comment_msg : comment_msg,
      }
    });
    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
      console.log(result.feedback);
      console.log(result.rating);
    });
  }


}
