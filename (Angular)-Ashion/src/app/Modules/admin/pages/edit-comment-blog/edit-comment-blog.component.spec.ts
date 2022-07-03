import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditCommentBlogComponent } from './edit-comment-blog.component';

describe('EditCommentBlogComponent', () => {
  let component: EditCommentBlogComponent;
  let fixture: ComponentFixture<EditCommentBlogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditCommentBlogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditCommentBlogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
