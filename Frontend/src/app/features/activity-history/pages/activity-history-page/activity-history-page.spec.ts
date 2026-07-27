import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityHistoryPage } from './activity-history-page';

describe('ActivityHistoryPage', () => {
  let component: ActivityHistoryPage;
  let fixture: ComponentFixture<ActivityHistoryPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActivityHistoryPage],
    }).compileComponents();

    fixture = TestBed.createComponent(ActivityHistoryPage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
