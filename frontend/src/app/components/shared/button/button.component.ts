import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'cmp-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.scss']
})
export class ButtonComponent implements OnInit {

  @Input() type: 'danger' | 'success' | 'info' = 'info'

  constructor() { }

  ngOnInit(): void {
  }

}
