import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCategoryBlogComponent } from './add-category-blog.component';

describe('AddCategoryBlogComponent', () => {
  let component: AddCategoryBlogComponent;
  let fixture: ComponentFixture<AddCategoryBlogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddCategoryBlogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCategoryBlogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
