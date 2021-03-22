import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';

import { Observable } from 'rxjs';
import { AdminService } from 'src/app/admin.service';
import { UserService } from 'src/app/user.service';
import { AlertmodelComponent } from '../alertmodel/alertmodel.component';
import { VerifyComponent } from '../verify/verify.component';

@Component({
  selector: 'app-userhome',
  templateUrl: './userhome.component.html',
  styleUrls: ['./userhome.component.scss']
})
export class UserhomeComponent implements OnInit {

  username
  activated
  isProfileCreated
  users: Observable<any>
  categories: any;
  slides: any 
  courses: any;
  progress=false
  home1=true
  completed=false
  EcourseDates:any
  FcourseDates:any
  Certificates:any
  EnrolledCourseList:any;
  FinishedCourseList:any;
  constructor(private router:Router,private uservice:UserService, private as: AdminService,public dialog: MatDialog) {

    this.username = sessionStorage.getItem("username")
    this.uservice.isActivated(sessionStorage.getItem("userId")).subscribe((x) => {
      this.activated = x
      //temporory
      this.activated=true
    })
    this.uservice.isProfileCreated(sessionStorage.getItem("userId")).subscribe((x)=>{
      this.isProfileCreated=x
       })
  }


  ngOnInit(): void {
    // get cetegories

    this.uservice.getECourses(sessionStorage.getItem("userId")).subscribe((x)=>{
      this.EnrolledCourseList=x
    })
    this.uservice.getFCourses(sessionStorage.getItem("userId")).subscribe((x)=>{
      this.FinishedCourseList=x
    })
    this.uservice.getEdates(sessionStorage.getItem("userId")).subscribe((X)=>{
      this.EcourseDates=X
    })
    this.uservice.getFdates(sessionStorage.getItem("userId")).subscribe((x)=>{
      this.FcourseDates=x
    })
    this.uservice.getCerties(sessionStorage.getItem("userId")).subscribe((x)=>{
      this.Certificates=x
    })
    this.as.getCategories()
    .subscribe((data)=>{
      this.categories=data;
      this.slides = this.chunk(this.categories, 3);
    },
    (err)=>{
      console.log('Error is:',err);
    });

    //get courses
     //get courses
     this.uservice.getCourses().subscribe((data)=>{
      this.courses=data
      console.log(this.courses)
    })
  }


  sendotp(){
    this.dialog.open(VerifyComponent,{
      width: '800px',
    });
    this.uservice.sendregotp(sessionStorage.getItem('userId')).subscribe((x)=>{
      console.log("otp sent")
      
      sessionStorage.setItem("otp",btoa(x))
    }
    )
  }
  
  chunk(arr, chunkSize) {
    let R = [];
    for (let i = 0, len = arr.length; i < len; i += chunkSize) {
      R.push(arr.slice(i, i + chunkSize));
    }
    return R;
  }

  view(courseid:any){
    // console.log(courseid)
    if(this.activated){
      this.router.navigate(['/coursedetail'],{queryParams:{courseid:btoa(courseid)}})

    }
    else{
      this.dialog.open(AlertmodelComponent,{
        data: {value:"You need to Verify your account First"}
      });
    }
    
  }


  home(){
    this.home1=true
    this.progress=false
    this.completed=false
  }

  pro(){
    this.home1=false
    this.progress=true
    this.completed=false
  }

  comp(){
    this.home1=false
    this.progress=false
    this.completed=true
  }
  sendcerti(courseid) {
    this.uservice.mailcerti(sessionStorage.getItem("userId"), courseid).subscribe((x) => {

    })

  }

}
