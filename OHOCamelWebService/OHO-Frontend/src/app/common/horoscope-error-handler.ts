/*
 * Copyright 2020 IKS Gesellschaft fuer Informations- und Kommunikationssysteme mbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import { ErrorHandler } from '@angular/core';

export class HoroscopeErrorHandler extends ErrorHandler
{
    static requestName: string;

    handleError(error: any)
    {
        if (error.status === 0) {
            alert('Dear ' + HoroscopeErrorHandler.requestName + ',\n\nmy service at ' + error.url + 
                  ' is currently not available.\n' +
            'Please retry later. I\'m happy to answer then.\n\nYours Scarlet');
        } else if (error.status === 500) {
            alert('Dear ' + HoroscopeErrorHandler.requestName + ',\n\nmy service at ' + error.url + 
                  ' has currently a problem.\n' +
            'Please retry later. I\'m happy to answer then.\n\nYours Scarlet');
        } else if (error.status === 400) {
            console.log(error);
            alert('The request to uri "' + error.url + '" is invalid!');
        } else if (error.status === 404) {
            alert('The ressouce with uri "' + error.url + '" is not reachable!');
        } else if (error.cause != null && (error.cause.status === 404)) {
            alert('The ressouce with uri "' + error.cause.url + '" is not reachable!');
        } else {
            console.log('Unexpected ERROR: ');
            console.log(error);
            alert('Unexpected Error!');
        }
    }
}