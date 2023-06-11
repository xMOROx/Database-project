import {Component, Inject, Input} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {CarsService} from "../../../features/services/cars.service";
import {LocationsService} from "../../../features/services/locations.service";

@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.scss']
})
export class RatingComponent {
  @Input() maxRating: number = 10;
  public maxRatingArray: any = [];
  @Input() public selectedStars: number;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              private carsService: CarsService,
              private locationsService: LocationsService,
              private dialog: MatDialogRef<RatingComponent>) {
    this.maxRatingArray = Array(this.maxRating).fill(0);
    this.selectedStars = data.rating;
  }

  handleMouseEnter(index: number) {
    this.selectedStars = index + 1;
  }

  handleMouseLeave() {
    this.selectedStars = this.data.rating;
  }

  rate(index: number) {
  }

  close() {
    this.dialog.close();
  }

}
