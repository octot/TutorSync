import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TuitionHistoryPage } from './tuition-history-page';

describe('TuitionHistoryPage', () => {
  let component: TuitionHistoryPage;
  let fixture: ComponentFixture<TuitionHistoryPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TuitionHistoryPage],
    }).compileComponents();

    fixture = TestBed.createComponent(TuitionHistoryPage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
