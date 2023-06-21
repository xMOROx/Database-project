import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {PaginationModel} from './models/pagination.model';
import {CarsService} from 'src/app/features/services/cars.service';
import {MatPaginator} from "@angular/material/paginator";
import {FormControl, FormGroup} from "@angular/forms";
import {LocationsService} from "../../services/locations.service";
import {SearchRequest} from "./models/SearchRequest.model";
import {Operator} from "./enums/Operator";
import {FieldType} from "./enums/FieldType";
import {Observable, startWith} from "rxjs";
import {map} from "rxjs/operators";

@Component({
    selector: 'app-content',
    templateUrl: './content.component.html',
    styleUrls: ['./content.component.scss']
})
export class ContentComponent implements OnInit {

    @ViewChild(MatPaginator) paginator!: MatPaginator;
    public contentType: string = '';
    // @ts-ignore
    public results: PaginationModel = {page: 0, results: undefined, total_pages: 0, total_results: 0};
    public offerType: string = 'all';
    public popularCities: any = [];

    public availableColors: any = [];
    public availableBrands: any = [];
    public availableModels: any = [];
    public availableBodyTypes: any = [];

    // ==============================================================================================================
    public colorsControl = new FormControl();
    public brandsControl = new FormControl();
    public modelsControl = new FormControl();
    public bodyTypesControl = new FormControl();
    public cityControl = new FormControl();

    public minPriceControl = new FormControl();
    public maxPriceControl = new FormControl();

    public minPowerControl = new FormControl();
    public maxPowerControl = new FormControl();

    public minProductionYearControl = new FormControl();
    public maxProductionYearControl = new FormControl();

    public minDoorsControl = new FormControl();
    public maxDoorsControl = new FormControl();

    public minSeatsControl = new FormControl();
    public maxSeatsControl = new FormControl();

    public range = new FormGroup({
        start: new FormControl<Date | null>(null),
        end: new FormControl<Date | null>(null),
    });

    public currentYear = new Date().getFullYear();
    public minDate = new Date();
    public maxDate = new Date(this.currentYear + 1, 11, 31);

    public isFrontWheelDrive: boolean = false;
    public isMetallic: boolean = false;
    public isExpanded: boolean = false;

    private searchRequest: SearchRequest = {
        Filters: [],
        Sorts: [],
        Page: 0,
        Size: 20,
        StartDate: undefined,
        EndDate: undefined,
    };
    // ==============================================================================================================


    public isLoading: boolean = true;
    public totalResults: any;
    public filteredCities!: Observable<string[]>;

    constructor(
        private carsService: CarsService,
        private router: Router,
        private locationsService: LocationsService
    ) {
        this.contentType = this.router.url.split('/')[1];
    }

    ngOnInit() {
        if (this.contentType === 'cars') {
            this.getCars(0);
            this.loadValuesForSearch();
            this.locationsService.getCities().subscribe((res: any) => {
                this.popularCities = res.content;
                this.filteredCities = this.cityControl.valueChanges.pipe(
                    startWith(''),
                    map(value => this._filter(value || '')),
                );
            });

        } else {
            this.getLocations(0);
            this.locationsService.getCities().subscribe((res: any) => {
                this.popularCities = res.content;
            });
        }
    }

    private _filter(value: string): string[] {
        const filterValue = value.toLowerCase();

        return this.popularCities.filter((option: string) => option.toLowerCase().includes(filterValue));
    }

    public getCars(page: number) {
        switch (this.offerType.toLowerCase()) {
            case 'all':
                this.getAllCars(page);
                break;
            case 'best offers':
                this.getBestOffers(page);
                break;
        }
    }

    public getLocations(page: number) {
        switch (this.offerType.toLowerCase()) {
            case 'all':
                this.getAllLocations(page);
                break;
            default:
                if (this.popularCities.find((city: any) => city.toLowerCase() === this.offerType.toLowerCase())) {
                    this.getLocationsByCity(page);
                }
                break;
        }
    }

    public getAllLocations(page: number) {
        this.locationsService.getLocations(page).subscribe((res: any) => {
            this.results.results = res._embedded.Locations;
            this.results.page = res.page.number;
            this.results.total_pages = res.page.totalPages;
            this.results.total_results = res.page.totalElements;
            this.totalResults = res.page.totalElements;
            this.isLoading = false;
        });
    }

    public getLocationsByCity(page: number) {
        this.locationsService.getLocationsByCity(this.offerType, page).subscribe((res: any) => {
            this.results.results = res._embedded.Locations;
            this.results.page = res.page.number;
            this.results.total_pages = res.page.totalPages;
            this.results.total_results = res.page.totalElements;
            this.totalResults = res.page.totalElements;
            this.isLoading = false;
        });
    }

    public getAllCars(page: number) {
        this.carsService.getVehicles(page).subscribe((res: any) => {
            console.log(res);
            this.results.results = res._embedded.Vehicles;
            this.results.page = res.page.number;
            this.results.total_pages = res.page.totalPages;
            this.results.total_results = res.page.totalElements;
            this.totalResults = res.page.totalElements;
            this.isLoading = false;
        });
    }

    public getBestOffers(page: number) {
        this.carsService.getBestOfferVehicles(page).subscribe((res: any) => {
            this.results.results = res._embedded.Vehicles;
            this.results.page = res.page.number;
            this.results.total_pages = res.page.totalPages;
            this.results.total_results = res.page.totalElements;
            this.totalResults = res.page.totalElements;
            this.isLoading = false;
        });
    }

    // search
    public getAvailableColors() {
        this.carsService.getVehicleColors().subscribe((res: any) => {
            this.availableColors = res.content;
        });
    }

    public getAvailableBrands() {
        this.carsService.getVehicleBrands().subscribe((res: any) => {
            this.availableBrands = res.content;
        });
    }

    public getAvailableModels() {
        this.carsService.getVehicleModels().subscribe((res: any) => {
            this.availableModels = res.content;
        });
    }

    public getAvailableBodyTypes() {
        this.carsService.getVehicleBodyTypes().subscribe((res: any) => {
            this.availableBodyTypes = res.content;
        });
    }

    public loadValuesForSearch() {
        this.getAvailableColors();
        this.getAvailableBrands();
        this.getAvailableModels();
        this.getAvailableBodyTypes();
    }

    public changePage(event: any) {
        if (this.contentType === 'cars') {
            this.getCars(event.pageIndex + 1);
        } else {
            this.getLocations(event.pageIndex + 1);
        }
    }

    public selectOffer(filterValue: string) {
        this.offerType = filterValue;
        this.paginator?.firstPage();
        this.resetFilters();
        this.isLoading = true;
        if (this.contentType === 'cars') {
            this.getCars(0);
        } else {
            this.getLocations(0);
        }
    }

    public applyFilters() {
        this.allFilters();

        if (this.contentType === 'cars') {
            this.carsService.searchVehicles(this.searchRequest).subscribe((res: any) => {
                if (res['page'] === null) {
                    this.results.results = [];
                    this.results.page = 0;
                    this.results.total_pages = 0;
                    this.results.total_results = 0;
                    this.totalResults = 0;
                    this.isLoading = false;
                    return;
                }

                this.results.results = res._embedded.Vehicles;
                this.results.page = res.page.number;
                this.results.total_pages = res.page.totalPages;
                this.results.total_results = res.page.totalElements;
                this.totalResults = res.page.totalElements;
                this.isLoading = false;
            });
        } else {

            this.locationsService.searchLocations(this.searchRequest).subscribe((res: any) => {
                if (res['page'] === null) {
                    this.results.results = [];
                    this.results.page = 0;
                    this.results.total_pages = 0;
                    this.results.total_results = 0;
                    this.totalResults = 0;
                    this.isLoading = false;
                    return;
                }

                this.results.results = res._embedded.Locations;
                this.results.page = res.page.number;
                this.results.total_pages = res.page.totalPages;
                this.results.total_results = res.page.totalElements;
                this.totalResults = res.page.totalElements;
                this.isLoading = false;
            });
        }
    }

    private clearSearchRequest() {
        this.searchRequest = {
            Filters: [],
            Sorts: [],
            Page: 0,
            Size: 20,
            StartDate: undefined,
            EndDate: undefined
        }
    }

    private allFilters() {
        if(this.contentType === 'cars') {
            this.clearSearchRequest();
            this.parsePrice();
            this.parsePower();
            this.parseDoors();
            this.parseSeats();
            this.parseProductionYear();
            this.parseBrands();
            this.parseModels();
            this.parseColors();
            this.parseBodyTypes();
            this.searchCity();
            this.dateRequest();
            return;
        } else {
            this.clearSearchRequest();
            this.parseCity();
            return;
        }


    }

    private parsePrice() {
        if (this.minPriceControl.value && this.maxPriceControl.value) {
            this.searchRequest.Filters.push({
                Key: "dailyFee",
                Operator: Operator.BETWEEN,
                FieldType: FieldType.DOUBLE,
                Value: this.minPriceControl.value,
                ValueTo: this.maxPriceControl.value
            });
            return;
        }

        if (this.minPriceControl.value) {
            this.searchRequest.Filters.push({
                Key: "dailyFee",
                Operator: Operator.GREATER_THAN_OR_EQUALS,
                FieldType: FieldType.DOUBLE,
                Value: this.minPriceControl.value
            });

            return;
        }

        if (this.maxPriceControl.value) {
            this.searchRequest.Filters.push({
                Key: "dailyFee",
                Operator: Operator.LESS_THAN_OR_EQUALS,
                FieldType: FieldType.DOUBLE,
                Value: this.maxPriceControl.value
            });
            return;
        }
    }

    private parsePower() {
        if (this.minPowerControl.value && this.maxPowerControl.value) {
            this.searchRequest.Filters.push({
                Key: "power",
                Operator: Operator.BETWEEN,
                FieldType: FieldType.DOUBLE,
                Value: this.minPowerControl.value,
                ValueTo: this.maxPowerControl.value
            });
            return;
        }

        if (this.minPowerControl.value) {
            this.searchRequest.Filters.push({
                Key: "power",
                Operator: Operator.GREATER_THAN_OR_EQUALS,
                FieldType: FieldType.DOUBLE,
                Value: this.minPowerControl.value
            });

            return;
        }

        if (this.maxPowerControl.value) {
            this.searchRequest.Filters.push({
                Key: "power",
                Operator: Operator.LESS_THAN_OR_EQUALS,
                FieldType: FieldType.DOUBLE,
                Value: this.maxPowerControl.value
            });
            return;
        }
    }

    private parseDoors() {
        if (this.minDoorsControl.value && this.maxDoorsControl.value) {
            this.searchRequest.Filters.push({
                Key: "doorsNumber",
                Operator: Operator.BETWEEN,
                FieldType: FieldType.DOUBLE,
                Value: this.minDoorsControl.value,
                ValueTo: this.maxDoorsControl.value
            });
            return;
        }

        if (this.minDoorsControl.value) {
            this.searchRequest.Filters.push({
                Key: "doorsNumber",
                Operator: Operator.GREATER_THAN_OR_EQUALS,
                FieldType: FieldType.DOUBLE,
                Value: this.minDoorsControl.value
            });

            return;
        }

        if (this.maxDoorsControl.value) {
            this.searchRequest.Filters.push({
                Key: "doorsNumber",
                Operator: Operator.LESS_THAN_OR_EQUALS,
                FieldType: FieldType.DOUBLE,
                Value: this.maxDoorsControl.value
            });
            return;
        }
    }

    private parseSeats() {
        if (this.minSeatsControl.value && this.maxSeatsControl.value) {
            this.searchRequest.Filters.push({
                Key: "seatsNumber",
                Operator: Operator.BETWEEN,
                FieldType: FieldType.DOUBLE,
                Value: this.minSeatsControl.value,
                ValueTo: this.maxSeatsControl.value
            });
            return;
        }

        if (this.minSeatsControl.value) {
            this.searchRequest.Filters.push({
                Key: "seatsNumber",
                Operator: Operator.GREATER_THAN_OR_EQUALS,
                FieldType: FieldType.DOUBLE,
                Value: this.minSeatsControl.value
            });

            return;
        }

        if (this.maxSeatsControl.value) {
            this.searchRequest.Filters.push({
                Key: "seatsNumber",
                Operator: Operator.LESS_THAN_OR_EQUALS,
                FieldType: FieldType.DOUBLE,
                Value: this.maxSeatsControl.value
            });
            return;
        }
    }

    private parseProductionYear() {
        if (this.minProductionYearControl.value && this.maxProductionYearControl.value) {
            this.searchRequest.Filters.push({
                Key: "productionYear",
                Operator: Operator.BETWEEN,
                FieldType: FieldType.DOUBLE,
                Value: this.minProductionYearControl.value,
                ValueTo: this.maxProductionYearControl.value
            });
            return;
        }

        if (this.minProductionYearControl.value) {
            this.searchRequest.Filters.push({
                Key: "productionYear",
                Operator: Operator.GREATER_THAN_OR_EQUALS,
                FieldType: FieldType.DOUBLE,
                Value: this.minProductionYearControl.value
            });

            return;
        }

        if (this.maxProductionYearControl.value) {
            this.searchRequest.Filters.push({
                Key: "productionYear",
                Operator: Operator.LESS_THAN_OR_EQUALS,
                FieldType: FieldType.DOUBLE,
                Value: this.maxProductionYearControl.value
            });
            return;
        }
    }

    private parseBrands() {
        if (this.brandsControl.value) {
            this.searchRequest.Filters.push({
                Key: "brand",
                Operator: Operator.IN,
                FieldType: FieldType.STRING,
                Values: this.brandsControl.value
            });
        }
    }

    private parseModels() {
        if (this.modelsControl.value) {
            this.searchRequest.Filters.push({
                Key: "model",
                Operator: Operator.IN,
                FieldType: FieldType.STRING,
                Values: this.modelsControl.value
            });
        }
    }

    private parseColors() {
        if (this.colorsControl.value) {
            this.searchRequest.Filters.push({
                Key: "color",
                Operator: Operator.IN,
                FieldType: FieldType.STRING,
                Values: this.colorsControl.value
            });
        }
    }

    private parseBodyTypes() {
        if (this.bodyTypesControl.value) {
            this.searchRequest.Filters.push({
                Key: "bodyType",
                Operator: Operator.IN,
                FieldType: FieldType.STRING,
                Values: this.bodyTypesControl.value
            });
        }
    }

    private dateRequest() {
        if (this.range.invalid) {
            alert('Invalid date range');
        }

        if (!this.range.value.start || !this.range.value.end) {
            alert('Start and end date are required!');
        }

        // @ts-ignore
        this.searchRequest.StartDate = this.createStringDate(this.range.value.start);
        // @ts-ignore
        this.searchRequest.EndDate = this.createStringDate(this.range.value.end);
    }

    private createStringDate(date: Date): string {
        let month: string, day: string, number: number;

        if (date.getMonth() < 10) {
            number = date.getMonth() + 1;
            month = '0' + number;
        } else {
            number = date.getMonth() + 1;
            month = `${number}`;
        }

        if (date.getDate() < 10) {
            day = '0' + date.getDate();
        } else {
            day = `${date.getDate()}`;
        }


        return date.getFullYear() + '-' + month + '-' + day;
    }

    private searchCity() {
        if (this.cityControl.value) {
            this.searchRequest.Filters.push({
                Key: "location.city",
                Operator: Operator.EQUALS,
                FieldType: FieldType.STRING,
                Value: this.cityControl.value
            });
        }
    }


    private parseCity() {
        if (this.cityControl.value) {
            this.searchRequest.Filters.push({
                Key: "city",
                Operator: Operator.IN,
                FieldType: FieldType.STRING,
                Values: this.cityControl.value
            });
        }
    }
    public resetFilters() {
        this.colorsControl.reset();
        this.brandsControl.reset();
        this.modelsControl.reset();
        this.bodyTypesControl.reset();
        this.cityControl.reset();
        this.minPriceControl.reset();
        this.maxPriceControl.reset();
        this.minPowerControl.reset();
        this.maxPowerControl.reset();
        this.minProductionYearControl.reset();
        this.maxProductionYearControl.reset();
        this.minDoorsControl.reset();
        this.maxDoorsControl.reset();
        this.minSeatsControl.reset();
        this.maxSeatsControl.reset();
        this.range.reset();
        this.isFrontWheelDrive = false;
        this.isMetallic = false;

        this.clearSearchRequest();
    }
}
