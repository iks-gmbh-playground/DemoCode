import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';
import { HttpErrorResponse, HttpHeaders, HttpClient } from '@angular/common/http';
import { HoroscopeRequestData } from '../common/domainobjects/gen/HoroscopeRequestData';
import { OhoConstants } from '../common/domainobjects/oho-constants';
import { HoroscopeErrorHandler } from '../common/horoscope-error-handler';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HoroscopeRequestDataService {

  constructor(private httpClient: HttpClient) { }

  sendToServer(horoscopeRequestData: HoroscopeRequestData) {
    const uri = environment.serverUrl;
    //console.log(uri);
    //console.log(horoscopeRequestData);
    HoroscopeErrorHandler.requestName = horoscopeRequestData.getName();

    return this.httpClient.post(uri, JSON.stringify(horoscopeRequestData), OhoConstants.getRestCallHeaderOptions())
                          .pipe(catchError((error: HttpErrorResponse) => {
                            throw error; }
                          ));
  }
}
