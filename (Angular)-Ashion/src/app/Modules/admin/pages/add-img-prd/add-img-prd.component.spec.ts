import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddImgPrdComponent } from './add-img-prd.component';

describe('AddImgPrdComponent', () => {
  let component: AddImgPrdComponent;
  let fixture: ComponentFixture<AddImgPrdComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddImgPrdComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddImgPrdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
