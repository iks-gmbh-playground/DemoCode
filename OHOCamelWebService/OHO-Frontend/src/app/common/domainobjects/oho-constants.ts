import { HttpHeaders } from '@angular/common/http';

export class OhoConstants
{
  public static getRestCallHeaderOptions2() {
    const ohoHeaders = new HttpHeaders();
    ohoHeaders.set('Content-Type', 'application/json');
    const httpOptions = { headers: ohoHeaders, responseType: 'text' as 'json'};
    return httpOptions;
  }


  public static getRestCallHeaderOptions() {
     const httpOptions = {
     headers: new HttpHeaders({'Content-Type': 'application/json'}),
     responseType: 'text' as 'json'};
     return httpOptions;
  }

}
