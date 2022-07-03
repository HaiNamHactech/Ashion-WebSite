import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InstaItemComponent } from './insta-item.component';

describe('InstaItemComponent', () => {
  let component: InstaItemComponent;
  let fixture: ComponentFixture<InstaItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InstaItemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InstaItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
