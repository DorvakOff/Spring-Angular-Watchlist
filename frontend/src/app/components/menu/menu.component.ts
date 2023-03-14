import {Component, OnInit} from '@angular/core';
import {TranslationService} from "../../services/translation.service";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  constructor(public translation: TranslationService) {

  }

  ngOnInit(): void {
  }

  changeLang(code: string) {
    this.translation.changeLang(code)
  }
}
