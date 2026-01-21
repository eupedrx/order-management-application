import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ordersdetails } from './ordersdetails';

describe('Ordersdetails', () => {
  let component: Ordersdetails;
  let fixture: ComponentFixture<Ordersdetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Ordersdetails]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ordersdetails);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
