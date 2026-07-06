import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenerateMessagePage } from './generate-message-page';

describe('GenerateMessagePage', () => {
  let component: GenerateMessagePage;
  let fixture: ComponentFixture<GenerateMessagePage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GenerateMessagePage],
    }).compileComponents();

    fixture = TestBed.createComponent(GenerateMessagePage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
