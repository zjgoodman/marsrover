import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { PhotoListComponent } from './photo-list/photo-list.component';
import { FormsModule } from '@angular/forms';
import { PhotoEntryComponent } from './photo-entry/photo-entry.component';

@NgModule( {
  declarations: [
    AppComponent,
    PhotoListComponent,
    PhotoEntryComponent
  ],
  imports: [
    BrowserModule, FormsModule, HttpClientModule
  ],
  providers: [],
  bootstrap: [ AppComponent ]
} )
export class AppModule { }
