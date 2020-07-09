import { BrowserModule } from '@angular/platform-browser';
import { HoroscopeRequestDataComponent } from './components/horoscope-request/horoscope-request-component';
import { HoroscopeRequestDataService } from './services/horoscope-request-service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgModule, ErrorHandler } from '@angular/core';
import { HoroscopeErrorHandler } from './common/horoscope-error-handler';
import { AppComponent } from './app.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    AppComponent, HoroscopeRequestDataComponent
  ],
  imports: [
    BrowserModule, FormsModule, ReactiveFormsModule, HttpClientModule,
    RouterModule.forRoot([
      {path: '', redirectTo: '/oho', pathMatch: 'full'},
      {path: 'oho', component: HoroscopeRequestDataComponent}
    ])
  ],
  providers: [HoroscopeRequestDataService,
  {provide: ErrorHandler, useClass: HoroscopeErrorHandler}],
  bootstrap: [AppComponent]
})
export class AppModule { }
