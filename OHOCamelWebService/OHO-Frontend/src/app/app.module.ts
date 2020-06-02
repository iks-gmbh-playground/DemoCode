import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HoroscopeRequestDataComponent } from './components/horoscope-request/horoscope-request-component';
import { HoroscopeRequestDataService } from './services/horoscope-request-service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';

@NgModule({
  declarations: [
    AppComponent, HoroscopeRequestDataComponent
  ],
  imports: [
    BrowserModule, FormsModule, ReactiveFormsModule, HttpClientModule
  ],
  providers: [HoroscopeRequestDataService],
  bootstrap: [AppComponent]
})
export class AppModule { }
