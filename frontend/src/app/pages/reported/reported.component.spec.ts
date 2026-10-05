import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportedComponent } from './reported.component';

describe('ReportedComponent', () => {
  let component: ReportedComponent;
  let fixture: ComponentFixture<ReportedComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReportedComponent]
    });
    fixture = TestBed.createComponent(ReportedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
