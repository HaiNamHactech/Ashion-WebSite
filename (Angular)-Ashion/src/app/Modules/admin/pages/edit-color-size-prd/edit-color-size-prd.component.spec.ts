import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditColorSizePrdComponent } from './edit-color-size-prd.component';

describe('EditColorSizePrdComponent', () => {
  let component: EditColorSizePrdComponent;
  let fixture: ComponentFixture<EditColorSizePrdComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditColorSizePrdComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditColorSizePrdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
