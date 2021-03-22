import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {MatButtonModule} from '@angular/material/button';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ErrorComponent } from './error/error.component';
import { UsermoduleModule } from './usermodule/usermodule.module';
import { AdminModule } from './admin/admin.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { AuthinterceptorService } from './authinterceptor.service';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { SharedModule } from './shared/shared.module';
import { SafePipe } from './safe.pipe';



@NgModule({
  declarations: [
    AppComponent,
    ErrorComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    UsermoduleModule,
    AdminModule,
    HttpClientModule,
    ReactiveFormsModule,
    NoopAnimationsModule,
    MatButtonModule,
   SharedModule,
  ],
  providers: [

    {
      provide:HTTP_INTERCEPTORS,
      useClass:AuthinterceptorService,
      multi:true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
