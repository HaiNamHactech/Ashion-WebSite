import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css'],
})
export class PaginationComponent implements OnInit {
  @Input() totalPage!: number;
  @Input() lastPage!: boolean;
  @Input() firstPage!: boolean;
  @Input() page!: number;
  @Input() pageSize!: number;
  @Output() selectPage = new EventEmitter();

  listPage!: Array<any>;

  constructor() {}
  ngOnChanges(changes: SimpleChanges): void {
    this.listPage = this.pagination(this.page, this.totalPage);
  }

  ngOnInit(): void {}

  getType(value: any) {
    return typeof value;
  }

  // ... panigation  //
  pagination(c: number, m: number) {
    var current = c,
      last = m,
      delta = 2,
      left = current - delta,
      right = current + delta,
      range = [],
      rangeWithDots = [],
      l;

    for (let i = 1; i <= last; i++) {
      if (i == 1 || i == last || (i >= left && i < right)) {
        range.push(i);
      }
    }

    for (let i of range) {
      if (l) {
        if (i - l === 2) {
          rangeWithDots.push(l + 1);
        } else if (i - l !== 1) {
          rangeWithDots.push('...');
        }
      }
      rangeWithDots.push(i);
      l = i;
    }

    return rangeWithDots;
  }

  moveToPage(idxPage: number): void {
    this.page = idxPage;
    this.listPage = this.pagination(this.page, this.totalPage);
    this.selectPage.emit(this.page);
  }

  gotoTop() {
    window.scroll({
      top: 0,
      left: 0,
      behavior: 'smooth',
    });
  }
}
