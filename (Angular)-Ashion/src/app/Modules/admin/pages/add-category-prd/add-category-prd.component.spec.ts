import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCategoryPrdComponent } from './add-category-prd.component';

describe('AddCategoryPrdComponent', () => {
  let component: AddCategoryPrdComponent;
  let fixture: ComponentFixture<AddCategoryPrdComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddCategoryPrdComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCategoryPrdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
