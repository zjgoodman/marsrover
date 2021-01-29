import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PhotoEntryComponent } from './photo-entry.component';

describe('PhotoEntryComponent', () => {
  let component: PhotoEntryComponent;
  let fixture: ComponentFixture<PhotoEntryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PhotoEntryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PhotoEntryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
