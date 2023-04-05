import {Component, ElementRef, EventEmitter, HostListener, Input, OnInit, Output} from '@angular/core';
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";

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

  @Input() placeholder: string = 'SEARCH_PLACEHOLDER_DEFAULT'
  placeholderValue: string = ''

  constructor(private eRef: ElementRef, private router: Router, private translate: TranslateService) {
    this.translate.get(this.placeholder).subscribe((res: string) => {
      this.placeholderValue = res
    })
    this.translate.onLangChange.subscribe(() => {
      this.translate.get(this.placeholder).subscribe((res: string) => {
        this.placeholderValue = res
      })
    })
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
      this.doSearch()
    }, 250)
  }

  doSearch() {
    this.onSearch.emit(this.keywords.trim())
    if (!this.keywords || !this.inputResults) {
      return
    }
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
