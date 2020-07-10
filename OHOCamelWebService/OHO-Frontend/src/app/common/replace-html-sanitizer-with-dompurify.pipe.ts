import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import DOMPurify from 'dompurify';

@Pipe({
  name: 'replaceHtmlSanitizerWithDompurify'
})
export class ReplaceHtmlSanitizerWithDompurifyPipe implements PipeTransform {

  constructor(protected nativeSanitizer: DomSanitizer) {}

  public transform(htmlContent: any): any {
    const sanitizedHtmlContent = DOMPurify.sanitize(htmlContent);
    return this.nativeSanitizer.bypassSecurityTrustHtml(sanitizedHtmlContent);
  }
}
