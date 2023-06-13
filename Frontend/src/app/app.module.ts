import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { PageNotFoundComponent } from './core/components/page-not-found/page-not-found.component';
import { ReactiveFormsModule } from "@angular/forms";
import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { AuthInterceptor } from "./authentication/helpers/auth.interceptor";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FooterComponent } from './core/components/footer/footer.component';
import { NavBarComponent } from './core/components/nav-bar/nav-bar.component';
import { SharedModule } from './shared/shared.module';
import { ContentModule } from './features/modules/content/content.module';
import { AuthenticationModule } from './authentication/authentication.module';
import { HomeComponent } from './features/home/home.component';
import { SwiperModule } from "swiper/angular";
import { UsersModule } from './features/modules/users/users.module';
import { CarsService } from './features/services/cars.service';
import { RatingComponent } from './shared/components/rating/rating.component';
import {AdminService} from "./features/services/admin.service";
@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent,
    HomeComponent,
    FooterComponent,
    NavBarComponent,
    RatingComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    SharedModule,
    ContentModule,
    AuthenticationModule,
    SwiperModule,
    UsersModule,
    HttpClientModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    HttpClient,
    CarsService,
    AdminService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
