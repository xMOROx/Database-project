/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { BookingsService } from './bookings.service';

describe('Service: Bookings', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BookingsService]
    });
  });

  it('should ...', inject([BookingsService], (service: BookingsService) => {
    expect(service).toBeTruthy();
  }));
});
