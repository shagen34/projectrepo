import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {AppRoutingModule} from './app-routing.module';
import { LibraryComponent } from './library/library.component';
import {Song} from './models/Song';
import { HomeComponent } from './home/home.component';

@NgModule({
  declarations: [
    AppComponent,
    LibraryComponent,
    HomeComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule
  ],
  providers: [Song],
  bootstrap: [AppComponent]
})
export class AppModule { }
