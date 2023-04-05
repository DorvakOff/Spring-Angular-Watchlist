import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'cmp-star-rating',
  templateUrl: './star-rating.component.html',
  styleUrls: ['./star-rating.component.scss']
})
export class StarRatingComponent implements OnInit {

  stars = [1, 2, 3, 4, 5]
  hoverStar = 0;

  @Output() onRate = new EventEmitter<number>();
  @Input() rating = 0;

  constructor() {
  }

  ngOnInit(): void {
  }

  getClass(star: number) {
    if (this.hoverStar > 0) {
      if (this.hoverStar >= star) {
        return 'fas fa-star selected';
      } else {
        return 'far fa-star';

      }
    } else {
      if (this.rating >= star) {
        return 'fas fa-star selected';
      } else {
        return 'far fa-star';
      }
    }
  }

  onStarClick(star: number) {
    this.rating = star;
    this.onRate.emit(star);
  }
}
