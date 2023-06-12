import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {PaginationModel} from './models/pagination.model';
import {CarsService} from 'src/app/features/services/cars.service';
import {MatPaginator} from "@angular/material/paginator";
import {FormControl} from "@angular/forms";
import {LocationsService} from "../../services/locations.service";

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
  public filterType: string = 'all';
  public popularCities: any = [];
  public isLoading: boolean = true;
  public totalResults: any;
  public query: string = '';


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
    } else {
      this.getLocations(0);
      this.locationsService.getCities().subscribe((res: any) => {
        this.popularCities = res.content;
      });
      }
    }

  public getCars(page: number) {
    switch (this.filterType.toLowerCase()) {
      case 'all':
        this.getAllCars(page);
        break;
      case 'best offers':
        this.getBestOffers(page);
        break;
    }
  }

  public getLocations(page: number) {
    switch (this.filterType.toLowerCase()) {
      case 'all':
        this.getAllLocations(page);
        break;
      default:
          if (this.popularCities.find((city: any) => city.toLowerCase() === this.filterType.toLowerCase())) {
            this.getLocationsByCity(page);
          }
        break;
    }
  }

  public getAllLocations(page:number) {
    this.locationsService.getLocations(page).subscribe((res: any) => {
      this.results.results = res._embedded.Locations;
      this.results.page = res.page.number;
      this.results.total_pages = res.page.totalPages;
      this.results.total_results = res.page.totalElements;
      this.totalResults = res.page.totalElements;
      this.isLoading = false;
    });
  }

  public getLocationsByCity(page:number) {
    this.locationsService.getLocationsByCity(this.filterType, page).subscribe((res: any) => {
      this.results.results = res._embedded.Locations;
      this.results.page = res.page.number;
      this.results.total_pages = res.page.totalPages;
      this.results.total_results = res.page.totalElements;
      this.totalResults = res.page.totalElements;
      this.isLoading = false;
    });
  }

  public getAllCars(page:number) {
    this.carsService.getVehicles(page).subscribe((res: any) => {
      this.results.results = res._embedded.Vehicles;
      this.results.page = res.page.number;
      this.results.total_pages = res.page.totalPages;
      this.results.total_results = res.page.totalElements;
      this.totalResults = res.page.totalElements;
      this.isLoading = false;
    });
  }

  public getBestOffers(page:number) {
    this.carsService.getBestOfferVehicles(page).subscribe((res: any) => {
      this.results.results = res._embedded.Vehicles;
      this.results.page = res.page.number;
      this.results.total_pages = res.page.totalPages;
      this.results.total_results = res.page.totalElements;
      this.totalResults = res.page.totalElements;
      this.isLoading = false;
    });
  }

  public changePage(event: any) {
    if (this.contentType === 'cars') {
      this.getCars(event.pageIndex + 1);
    } else {
      this.getLocations(event.pageIndex + 1);
    }
  }

  public applyFilter(filterValue: string) {
    this.query = '';
    this.filterType = filterValue;
    this.paginator?.firstPage();
    if (this.contentType === 'cars') {
      this.getCars(0);
    } else {
      this.getLocations(0);
    }
  }

  private searchCars(query: string, page: any = 0) {

  }

  private searchLocations(query: string, page: any = 0) {
  }

}
