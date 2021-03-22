import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AuthenticateService } from 'src/app/authenticate.service';
import { UserService } from 'src/app/user.service';
import { AlertmodelComponent } from 'src/app/usermodule/alertmodel/alertmodel.component';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  constructor(private uservice: UserService, private router: Router, private as: AuthenticateService, public dialogRef: MatDialogRef<RegisterComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,private formbuilder:FormBuilder,public dialog: MatDialog) { }

  ngOnInit(): void {
    this.registerForm = this.formbuilder.group({
      username: new FormControl("",[Validators.required,Validators.minLength(5)]),
      email: new FormControl("",[Validators.required,Validators.pattern("[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$")]),
      password: new FormControl("",[Validators.required,Validators.pattern("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,10}$")])

    })

    this.loginForm = new FormGroup({
      username: new FormControl("adit7"),
      password: new FormControl("adit7")

    })

    this.forgotform=new FormGroup({
      email:new FormControl(),
      otp:new FormControl(),
      password:new FormControl(),
      cpassword:new FormControl()
    })
  }

  

  isLocked
  wpassword
  wusername
  failedattempts
  regshow = true
  loginshow = false
  forgotshow = false
  registerForm: FormGroup
  loginForm: FormGroup
  email
  username
  forgotform: FormGroup
  otpblock:boolean
  fields:boolean
  temp:any = true
  match = true
  matchpassword = true
  resendotp


  timeLeft: number = 30;
  interval;


  otpresend(){
    if(this.resendotp){
      this.timeLeft = 30;
      this.resendotp=false
      
      this.starttimer()
      this.otp()
      
    }else{}

  }



  hideshow() {
    this.regshow = true
    this.loginshow = false
  }

  hideshow1(){
    this.regshow = false
    this.loginshow = true
  }

 
  registeruser() {
    this.uservice.checkEmail(this.registerForm.value.email).subscribe((x) => {
      this.email = x

      if (!x) {
        this.uservice.checkUsername(this.registerForm.value.username).subscribe((x) => {
          this.username = x
          if (!x) {
            this.uservice.addUser(this.registerForm.value).subscribe((x) => {
              this.dialogRef.close();
              this.dialog.open(AlertmodelComponent,{
                data: {value:"Successfully Registered"}
              });
            })

          }

        })

      }
      else {
        this.uservice.checkUsername(this.registerForm.value.username).subscribe((x) => {
          this.username = x
        })

      }

    })


  }

  
  loginuser(){

    this.as.isLocked(this.loginForm.value.username).subscribe((data)=>{

      if(data===true){
        this.isLocked=true
       this.router.navigate(["/"])

      }
      else{
        this
        .as
        .authenticate(this.loginForm.value.username, this.loginForm.value.password)
        .subscribe(
          (data:any) => {
            console.log(data)
            this.as.clearfailedattempt(sessionStorage.getItem("userId")).subscribe((x)=>{
              this.dialogRef.close();

            })
             if(data.role==="user"){
               this.router.navigate(["/user"])
             }
            // if(data.roles[0]==="ROLE_ADMIN"){
            //   this.router.navigate(["/admin"])
            // }
          },
          (err)=>{
            this.as.incrementfailedattempt(this.loginForm.value.username).subscribe((data)=>{
              console.log("incremented");
              if(data>-1){
                this.wpassword=true
                this.wusername=false
                this.isLocked=false
                this.failedattempts=data
                if(data===3){
                  this.isLocked=true
                }
              }
              if(data===-1){
                this.wusername=true
                this.wpassword=false
                this.isLocked=false
              }
              
            })
            console.log("incremented")
          }
        )
        
      }

    })
  

  }

  forgot(){
    this.forgotshow=true
    this.loginshow=false
  }
  starttimer(){
    this.uservice.checkEmail(this.forgotform.value.email).subscribe((x)=>{
      if(x){
        this.interval = setInterval(() => {
          if(this.timeLeft===0){
            this.resendotp=true
            //this.timeLeft=30;
            clearInterval(this.interval);
          }
          if(this.timeLeft > 0) {
            
            this.timeLeft--;
          }
          // } else {
          //  
          //  // this.resendotp=false
          // }
        },1000)
        this.otp()

      }
    })
   

  }

  otp(){

   
    this.uservice.checkEmail(this.forgotform.value.email).subscribe((x)=>{
      this.temp=x
      console.log(x)
      if(x){
         this.otpblock=true
    this.fields=false
    this.uservice.forgotopt(this.forgotform.value.email).subscribe((x)=>{
      sessionStorage.setItem("value",btoa(x.toString()))
    })

      }
    })
   
  }

  verifyotp(){
    if(this.forgotform.value.otp===atob(sessionStorage.getItem("value"))){
      this.fields=true
      this.match=true
    }
    else{
      this.match=false
    }
  }

  submit(){
    if(this.forgotform.value.password===this.forgotform.value.cpassword){
      this.uservice.changepass(this.forgotform.value.email,this.forgotform.value.password).subscribe((x)=>{
        console.log("password changed")
        this.matchpassword=true
        this.dialogRef.close();
        // this.router.navigate(['/login'])
      })
    }
    else{
      this.matchpassword=false
    }
  }


}
