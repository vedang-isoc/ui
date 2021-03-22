import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticateService } from 'src/app/authenticate.service';
import { RegisterComponent } from '../register/register.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

username: any
 userrole:Observable<String>
  constructor(private as:AuthenticateService , private route:Router,public dialog: MatDialog) {
    this.userrole=this.as.userrole
   }

  ngOnInit(): void {
    this.username=sessionStorage.getItem("username")
  }

  reg(){
    this.dialog.open(RegisterComponent,{
      width: '800px',
    });
  }

  logout(){
    this.as.logout()
    this.route.navigate(["/"])
  }

}
