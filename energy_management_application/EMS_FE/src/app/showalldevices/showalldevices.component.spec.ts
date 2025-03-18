import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowalldevicesComponent } from './showalldevices.component';

describe('ShowalldevicesComponent', () => {
  let component: ShowalldevicesComponent;
  let fixture: ComponentFixture<ShowalldevicesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowalldevicesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ShowalldevicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
