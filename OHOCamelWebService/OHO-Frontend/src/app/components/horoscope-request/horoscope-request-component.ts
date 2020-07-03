import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { HoroscopeRequestDataService } from 'src/app/services/horoscope-request-service';
import { HoroscopeRequestData } from 'src/app/common/domainobjects/gen/HoroscopeRequestData';
import { HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'horoscope-request',
  templateUrl: './horoscope-request-component.html',
  styleUrls: ['./horoscope-request-component.css']
})
export class HoroscopeRequestDataComponent implements OnInit {

  form: FormGroup;
  horoscope: string;
  text = true;
  favouriteColor = '#cccccc';

  constructor(private horoscopeRequestDataService: HoroscopeRequestDataService) {
      this.form = this.createFormControl();
  }

  ngOnInit(): void { 
    this.colorpickerC.setValue(this.favouriteColor);
  }

  submit() {
    const horoscopeRequestData = this.getHoroscopeRequestData();
    this.horoscopeRequestDataService.sendToServer(horoscopeRequestData)
                                    .subscribe((response) => {
                                        const html = response as string; 
                                        if (html.length === 0) {
                                          throw new HttpErrorResponse({status: 500, url: environment.serverUrl});
                                        } else {
                                          this.horoscope = html;
                                        }
                                    });
  }

  reset() {
    this.horoscope = null;
  }

  // The form control block below is generated - do not modify manually!
  createFormControl() {
    return new FormGroup({
        nameControl: new FormControl('', [
                Validators.required,
              ]),
        genderControl: new FormControl('d', [
                Validators.required,
              ]),
        birthdayControl: new FormControl('', [
                Validators.required,
              ]),
        colorpickerControl: new FormControl('', [
                Validators.required,
              ]),
    });
  }

  get nameC() {
    return this.form.get('nameControl');
  }

  get genderC() {
    return this.form.get('genderControl');
  }

  get birthdayC() {
    return this.form.get('birthdayControl');
  }

  get colorpickerC() {
    return this.form.get('colorpickerControl');
  }

  private getHoroscopeRequestData() {
    const horoscopeRequestData = new HoroscopeRequestData(null);

    horoscopeRequestData.setName(this.nameC.value);
    horoscopeRequestData.setGender(this.genderC.value);
    horoscopeRequestData.setBirthday(this.birthdayC.value);
    horoscopeRequestData.setFavouriteColor(this.colorpickerC.value);

    return horoscopeRequestData;
  }

  private setDataToControls(horoscopeRequestData: HoroscopeRequestData) {
    this.nameC.setValue(horoscopeRequestData.getName());
    this.genderC.setValue(horoscopeRequestData.getGender());
    this.birthdayC.setValue(horoscopeRequestData.getBirthday());
  }
  // The form control block above is generated - do not modify manually!

}
