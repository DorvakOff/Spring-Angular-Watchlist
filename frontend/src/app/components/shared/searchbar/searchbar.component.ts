import {Component, ElementRef, EventEmitter, HostListener, Input, OnInit, Output} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'cmp-searchbar',
  templateUrl: './searchbar.component.html',
  styleUrls: ['./searchbar.component.scss']
})
export class SearchbarComponent implements OnInit {
  keywords: string = ''
  timeout: any = null

  @Output() onSearch: EventEmitter<string> = new EventEmitter<string>()
  @Input() inputResults: SearchResult[] = []
  @Input() width: string = '100%'
  @Input() shouldShowResults: boolean = true
  @Input() defaultKeywords: string = ''

  results: SearchResult[] = []
  hideResults: boolean = true

  constructor(private eRef: ElementRef, private router: Router) {
  }

  @HostListener('document:click', ['$event'])
  onClickOutside(event: any) {
    if (!this.eRef.nativeElement.contains(event.target)) {
      this.hideResults = true
    }
  }

  ngOnInit(): void {
    this.keywords = this.defaultKeywords.trim()
  }

  onKeySearch($event: KeyboardEvent) {
    clearTimeout(this.timeout)
    if ($event.key === 'Enter') {
      this.doSearch()
      return
    }
    this.timeout = setTimeout(() => {
      if (this.keywords.trim().length >= 2) {
        this.doSearch()
      }
    }, 250)
  }

  doSearch() {
    if (!this.keywords || !this.inputResults) {
      return
    }
    this.onSearch.emit(this.keywords.trim())
    this.results = this.inputResults.filter((result: SearchResult) => {
      return result.title.trim().toLowerCase().includes(this.keywords.trim().toLowerCase())
    })
    if (this.shouldShowResults) {
      this.hideResults = false
    }
  }

  redirect(url: string) {
    if (!url) {
      return
    }

    if (url.startsWith('http')) {
      window.open(url, '_blank')
    } else {
      this.router.navigateByUrl(url).catch(() => {
      }).then(r => {
        if (!r) {
          console.error('Could not navigate to ' + url)
        }
      })
    }
  }
}

export interface SearchResult {
  title: string
  url: string
}
