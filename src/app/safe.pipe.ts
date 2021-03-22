import { Pipe, PipeTransform} from '@angular/core';
import { DomSanitizer } from "@angular/platform-browser";

@Pipe({ name: 'safe' })

export class SafePipe implements PipeTransform {

constructor(private sanitizer: DomSanitizer) { }
transform(value:any,...args:any[]):any {
  console.log(args[0])
 return this.sanitizer.bypassSecurityTrustResourceUrl("./assets/"+args[0]);
  }
}