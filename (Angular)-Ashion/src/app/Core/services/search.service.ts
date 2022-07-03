import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SearchService {
  private listSearchPrd = new BehaviorSubject('');

  public get ValuePageNumber() {
    return this.listSearchPrd;
  }

  public notifySearchValue(listSearch: any) {
    this.listSearchPrd.next(listSearch);
  }

  constructor() {}
}
