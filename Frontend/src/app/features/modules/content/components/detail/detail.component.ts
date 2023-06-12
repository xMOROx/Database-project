import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { take, throwError } from 'rxjs';
import { CarsService } from 'src/app/features/services/cars.service';
import { ContentModel } from '../../models/Content.model';
import { CarModel } from '../../models/Car.model';
import { PaginationModel } from '../../models/pagination.model';
import { Location } from '../../models/Location';
import { User } from "../../../../../authentication/models/User";
import { StorageService } from "../../../../../authentication/services/storage.service";
import { catchError } from "rxjs/operators";
import { RatingComponent } from "../../../../../shared/components/rating/rating.component";
import { LocationsService } from "../../../../services/locations.service";

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {
  public contentType: string = '';
  public content?: Partial<CarModel | Location | any>;

  public similarContentList: Array<PaginationModel> = [];
  public recommendationContentList: Array<PaginationModel> = [];
  public video?: ContentModel;
  public isLoading: boolean = true;
  public rating?: any;
  public user?: User;

  @ViewChild('matTrailerDialog') matTrailerDialog!: TemplateRef<any>;

  constructor(
    private carsService: CarsService,
    private route: ActivatedRoute,
    private router: Router,
    private sanitizer: DomSanitizer,
    public trailerDialog: MatDialog,
    private storage: StorageService,
    private dialog: MatDialog,
    private locationsService: LocationsService
  ) {
    this.contentType = this.router.url.split('/')[1];

  }

  ngOnInit() {
    this.user = this.storage.getUser();
    this.route.params.subscribe(params => {
      const id = params['id'];
      if (this.contentType === 'cars') {
        this.carsService.getVehicleById(id).pipe(take(1)).subscribe((car: CarModel) => {
          this.content = car;
          this.carsService.getVehicleParameters(car.vehicleParametersId).pipe(take(1)).subscribe((parameters: any) => {
            {
              this.content = { ...this.content, vehicleParameters: parameters };
              this.isLoading = false;
            }
          });
        });
      } else {
      }
    });
  }

  public openDialog(): void {
    const dialogRef = this.trailerDialog.open(this.matTrailerDialog, { backdropClass: 'backdropBackground' },);
    dialogRef.disableClose = false;
  }


  public openRatingDialog() {
    let dialogRating = this.dialog.open(RatingComponent, {
      minWidth: '200px',
      width: '30vw',
      maxWidth: '600px',
      height: 'auto',
      data: {
        rating: this.rating != undefined ? this.rating : 0,
        contentId: this.content?.['id'],
        userId: this.user?.id,
        contentType: this.contentType,
        title: this.content?.['title'] ?? this.content?.['name']
      },
      backdropClass: 'backdropBackground'
    }).afterClosed().subscribe(rating => {
      if (rating != null) {
        this.rating = rating;
      }
    });
  }

  protected readonly window = window;
}
