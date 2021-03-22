import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HeaderComponent } from './header/header.component';
import { IndexComponent } from './index/index.component';
import { RegisterComponent } from './register/register.component';
import { RouterModule, Routes } from '@angular/router';
import { MatTabsModule } from '@angular/material/tabs';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';


const routes: Routes = [
  
];

@NgModule({
  declarations: [ForgotPasswordComponent, HeaderComponent, IndexComponent, RegisterComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(routes), 
    MatTabsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatButtonModule,
  ],
  exports:[
    HeaderComponent,
    RegisterComponent,
  ]
})
export class SharedModule { }
