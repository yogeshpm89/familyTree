import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppBlockUiComponent } from './app-block-ui.component';

describe('AppBlockUiComponent', () => {
  let component: AppBlockUiComponent;
  let fixture: ComponentFixture<AppBlockUiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppBlockUiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppBlockUiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
