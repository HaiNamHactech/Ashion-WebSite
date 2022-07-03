import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TableService {
  private countValue = new BehaviorSubject('');

  public get ValueFromChild() {
    return this.countValue;
  }

  public notifyCountValue(number: any) {
    this.countValue.next(number);
  }
}
