import {Component, Input, OnInit} from '@angular/core';
import {BookingsService} from "../../../features/services/bookings.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-poster-card',
  templateUrl: './poster-card.component.html',
  styleUrls: ['./poster-card.component.scss']
})
export class PosterCardComponent implements OnInit {

  @Input() public model: any;
  @Input() public isCar: any;


  constructor(private bookingService: BookingsService, private router: Router) { }

  ngOnInit() {
  }

  public cancelBooking() {
    this.bookingService.cancelBooking(this.model.id).subscribe();
  }

}
