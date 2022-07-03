import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SizeColorPrdComponent } from './size-color-prd.component';

describe('SizeColorPrdComponent', () => {
  let component: SizeColorPrdComponent;
  let fixture: ComponentFixture<SizeColorPrdComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SizeColorPrdComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SizeColorPrdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
