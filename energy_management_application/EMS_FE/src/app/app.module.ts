import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { FormsModule, ReactiveFormsModule  } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { LogoutComponent } from './logout/logout.component';
import { Routes } from '@angular/router';
import { AuthInterceptor } from './AuthInterceptor';
import { NgChartsModule } from 'ng2-charts';
import { AdminPageComponent } from './admin-page/admin-page.component';
import { ClientPageComponent } from './client-page/client-page.component';
import { ShowalldevicesComponent } from './showalldevices/showalldevices.component';
import { JwtModule } from '@auth0/angular-jwt';
import { ShowallusersComponent } from './showallusers/showallusers.component';
import { MonitoringChartComponent } from './monitoring-chart/monitoring-chart.component';
import { ChatComponent } from './chat/chat.component';
import { RegisterComponent } from './register/register.component';
import { RegisterUserService } from './register-user.service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LogoutComponent,
    AdminPageComponent,
    ClientPageComponent,
    ShowalldevicesComponent,
    ShowallusersComponent,
    MonitoringChartComponent,
    ChatComponent,
    RegisterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgChartsModule,
    ReactiveFormsModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: () => localStorage.getItem('accessToken'),
        allowedDomains: ['localhost', 'devices.localhost', 'users.localhost', 'monitoring.localhost', 'chat.localhost'], ///'localhost'
      },
    }),
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
