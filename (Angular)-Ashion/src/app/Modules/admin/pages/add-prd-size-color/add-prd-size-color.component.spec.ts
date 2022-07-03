import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddPrdSizeColorComponent } from './add-prd-size-color.component';

describe('AddPrdSizeColorComponent', () => {
  let component: AddPrdSizeColorComponent;
  let fixture: ComponentFixture<AddPrdSizeColorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddPrdSizeColorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddPrdSizeColorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
