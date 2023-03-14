import {Component} from '@angular/core';
import {TranslationService} from "./services/translation.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'frontend';

  constructor(_translate: TranslationService) {
  }
}
