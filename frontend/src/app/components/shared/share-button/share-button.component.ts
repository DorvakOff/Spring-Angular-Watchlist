import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'cmp-share-button',
  templateUrl: './share-button.component.html',
  styleUrls: ['./share-button.component.scss']
})
export class ShareButtonComponent implements OnInit {

  @Input() path: string = ''
  clicked: boolean = false
  timerId?: number

  constructor() {
  }

  ngOnInit(): void {
  }

  copyUrl() {
    this.clicked = true
    if (this.timerId) window.clearTimeout(this.timerId)
    this.timerId = window.setTimeout(() => {
      this.clicked = false
    }, 2000)
    let url = window.location.origin + this.path
    let input = document.createElement('input')
    input.value = url
    document.body.appendChild(input)
    input.select()
    input.setSelectionRange(0, 99999)
    document.execCommand('copy')
    document.body.removeChild(input)
  }
}
