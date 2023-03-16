import {Component, OnInit} from '@angular/core';
import {TranslationService} from "../../services/translation.service";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  constructor(public translation: TranslationService, public userService: UserService) {

  }

  ngOnInit(): void {
  }

  changeLang(code: string) {
    this.translation.changeLang(code)
  }
}
