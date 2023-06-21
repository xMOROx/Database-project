import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {MatDialog,} from '@angular/material/dialog';
import {DomSanitizer} from '@angular/platform-browser';
import {ActivatedRoute, Router} from '@angular/router';
import {take,} from 'rxjs';
import {CarsService} from 'src/app/features/services/cars.service';
import {CarModel} from '../../models/Car.model';
import {PaginationModel} from '../../models/pagination.model';
import {Location} from '../../models/Location';
import {User} from "../../../../../authentication/models/User";
import {StorageService} from "../../../../../authentication/services/storage.service";
import {LocationsService} from "../../../../services/locations.service";
import {FormControl, FormGroup} from "@angular/forms";
import {BookingsService} from "../../../../services/bookings.service";

@Component({
    selector: 'app-detail',
    templateUrl: './detail.component.html',
    styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {
    protected readonly window = window;

    private readonly currentDate = new Date();
    public totalCost: number = 0;
    public numberOfDays: number = 0;
    public minDate: Date = new Date(this.currentDate.getFullYear(), this.currentDate.getMonth(), this.currentDate.getDate());
    public maxDate = new Date(this.currentDate.getFullYear() + 1, 11, 31);

    public range = new FormGroup({
        start: new FormControl<Date | null>(this.minDate),
        end: new FormControl<Date | null>(null),
    });

    public vehiclesForLocationContentList: PaginationModel = {page: 0, results: [], total_pages: 0, total_results: 0};

    public contentType: string = '';
    public content?: Partial<CarModel | Location | any>;
    public isLoading: boolean = true;
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
        private locationsService: LocationsService,
        private bookingsService: BookingsService,
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
                    this.isLoading = false;
                });
            } else {
                this.locationsService.getLocation(id).pipe(take(1)).subscribe((location: Location) => {
                    this.content = location;

                    this.locationsService.getAvailableVehicles(location.id,
                        this.convertDateToString(this.currentDate), this.convertDateToString(new Date(this.currentDate.getFullYear() + 1, this.currentDate.getMonth(), this.currentDate.getDate())), 0)
                        .pipe(take(1)).subscribe((vehicles) => {

                        if (vehicles['page'] === null) {
                            this.vehiclesForLocationContentList.results = vehicles;
                            this.vehiclesForLocationContentList.page = 0;
                            this.vehiclesForLocationContentList.total_pages = 0;
                            this.vehiclesForLocationContentList.total_results = 0;
                            this.isLoading = false;
                            return;
                        }

                        this.vehiclesForLocationContentList.results = vehicles._embedded.Vehicles;
                        this.vehiclesForLocationContentList.page = vehicles.page.number;
                        this.vehiclesForLocationContentList.total_pages = vehicles.page.totalPages;
                        this.vehiclesForLocationContentList.total_results = vehicles.page.totalElements;
                        this.isLoading = false;
                    });
                });
            }
        });
    }

    public openDialog(): void {
        const dialogRef = this.trailerDialog.open(this.matTrailerDialog, {backdropClass: 'backdropBackground'},);
        dialogRef.disableClose = false;
    }

    public reserveVehicle() {
        if (this.range.value.start === null) {
            return;
        }

        if (this.range.value.end === null) {
            return;
        }

        this.calculateTotalCost();

        this.route.params.subscribe(params => {
            const id = params['id'];
            this.carsService.getLocationForVehicle(id).pipe(take(1)).subscribe((locationID: number) => {
                const data = {
                    VehicleID: this.content?.['id'],
                    LocationID:locationID,
                    UserID: this.storage.getUser().id,
                    ReceiptDate: this.convertDateToString(this.range.value.start),
                    ReturnDate: this.convertDateToString(this.range.value.end),
                    TotalCost: this.totalCost,
                }

                this.bookingsService.reserveVehicle(data).pipe(take(1)).subscribe(
                    {
                        next: () => {
                            alert('Vehicle reserved successfully!');
                            this.dialog.closeAll();
                            this.router.navigate(['/dashboard/bookings']);
                        },
                        error: (err: any) => {
                            this.dialog.closeAll();
                            this.clearReservationValues();
                            if(err.status === 400) {
                                alert('Something went wrong!');
                            } else if (err.status === 409) {
                                alert('Vehicle is already reserved for this period!');
                            }
                        }
                    }
                );

            });
        });
    }

    public calculateTotalCost() {
        // @ts-ignore
        const days = Math.floor((this.range.value.end.getTime() - this.range.value.start.getTime()) / (1000 * 60 * 60 * 24));
        this.totalCost = days * this.content?.['dailyFee']!;
        this.numberOfDays = days;
    }

    public clearReservationValues() {
        this.range.reset();
        this.totalCost = 0;
        this.numberOfDays = 0;
    }

    private convertDateToString(date: any): string {
        let day = date.getDate();
        let month = date.getMonth() + 1;
        let year = date.getFullYear();

        if (day < 10 && month < 10) {
            return `${year}-0${month}-0${day}`;
        }

        if (day < 10) {
            return `${year}-${month}-0${day}`;
        }

        if (month < 10) {
            return `${year}-0${month}-${day}`;
        }

        return `${year}-${month}-${day}`;
    }

}
