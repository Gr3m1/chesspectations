import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PastMatchModalComponent } from './past-match-modal.component';

describe('PastMatchModalComponent', () => {
  let component: PastMatchModalComponent;
  let fixture: ComponentFixture<PastMatchModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PastMatchModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PastMatchModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
