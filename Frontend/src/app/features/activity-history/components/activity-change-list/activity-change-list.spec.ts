import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityChangeList } from './activity-change-list';

describe('ActivityChangeList', () => {
  let component: ActivityChangeList;
  let fixture: ComponentFixture<ActivityChangeList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActivityChangeList],
    }).compileComponents();

    fixture = TestBed.createComponent(ActivityChangeList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
