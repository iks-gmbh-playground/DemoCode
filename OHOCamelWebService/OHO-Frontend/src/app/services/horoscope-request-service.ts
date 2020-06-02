import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';
import { HttpErrorResponse, HttpHeaders, HttpClient } from '@angular/common/http';
import { HoroscopeRequestData } from '../common/domainobjects/gen/HoroscopeRequestData';
import { OhoConstants } from '../common/domainobjects/oho-constants';

@Injectable({
  providedIn: 'root'
})
export class HoroscopeRequestDataService {
  url = 'http://localhost:10003/oho/request';

  constructor(private httpClient: HttpClient) { }

  sendToServer(horoscopeRequestData: HoroscopeRequestData) {
    const uri = this.url;
    //console.log(uri);
    //console.log(horoscopeRequestData);

    return this.httpClient.post(this.url, JSON.stringify(horoscopeRequestData), OhoConstants.getRestCallHeaderOptions())
                          .pipe(catchError((error: HttpErrorResponse) => {
                            throw error; }
                          ));
  }
}
