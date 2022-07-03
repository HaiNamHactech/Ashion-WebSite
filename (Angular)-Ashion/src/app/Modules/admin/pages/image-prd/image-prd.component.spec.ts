import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImagePrdComponent } from './image-prd.component';

describe('ImagePrdComponent', () => {
  let component: ImagePrdComponent;
  let fixture: ComponentFixture<ImagePrdComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ImagePrdComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ImagePrdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
