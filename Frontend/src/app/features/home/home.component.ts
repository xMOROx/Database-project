import {Component, OnInit,} from '@angular/core';
import SwiperCore, {Pagination, SwiperOptions,} from 'swiper';
import {CarModel} from 'src/app/features/modules/content/models/Car.model';
import {Location} from 'src/app/features/modules/content/models/Location';
import {CarsService} from "src/app/features/services/cars.service";
import {LocationsService} from "../services/locations.service";
import {take} from "rxjs/operators";

SwiperCore.use([Pagination]);

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  public config: SwiperOptions = {
    slidesPerView: 2.3,
    spaceBetween: 20,
    navigation: true,
    watchSlidesProgress: true,
    grabCursor: true,
    pagination: {clickable: true,},
    scrollbar: {draggable: true},
    breakpoints: {
      992: {slidesPerView: 6.3, spaceBetween: 20, slidesOffsetBefore: 0, slidesOffsetAfter: 0},
      768: {slidesPerView: 4.3, spaceBetween: 15, slidesOffsetBefore: 0, slidesOffsetAfter: 0},
      576: {slidesPerView: 3.3, spaceBetween: 15, slidesOffsetBefore: 0, slidesOffsetAfter: 0},
      320: {slidesPerView: 2.3, spaceBetween: 10, slidesOffsetBefore: 10, slidesOffsetAfter: 10},
    }
  };

  public carsTabList = ["Cars", "BestOffer"];
  public carsList: Array<CarModel> = [];
  public selectedCarsTab = 0;

  public locationsTabList:Array<string> = [];
  public locationsList: Array<Location> = [];
  public selectedLocation = 0;


  constructor(private moviesService: CarsService, private locationService: LocationsService) {
  }

  ngOnInit() {
    this.getVehicles(this.carsTabList[this.selectedCarsTab]);
    this.locationService.getCities().pipe(take(1)).subscribe((cities:any) => {
      this.locationsTabList = cities?.['content'].slice(0, 5);
      this.getLocations(this.locationsTabList[this.selectedLocation]);
    });
  }

  private getNormalVehiclesOffer(page: number = 0) {
    this.moviesService.getVehicles(page).subscribe((cars) => {
      this.carsList = cars?._embedded?.Vehicles;
    });
  }

  private getBestOfferVehicles(page: number = 0) {
    this.moviesService.getBestOfferVehicles(page).subscribe((cars) => {
      this.carsList = cars?._embedded?.Vehicles;
    });
  }

  private getVehicles(tabName: string) {
    if (tabName === 'Cars') {
      this.getNormalVehiclesOffer();
    } else if (tabName === 'BestOffer') {
      this.getBestOfferVehicles();
    }
  }

  private getLocations(tabName: string) {
    this.locationService.getLocationsByCity(tabName).subscribe((locations) => {
      this.locationsList = locations?._embedded?.Locations;
    } );
  }

  public tabCarsChange(event: any) {
    this.selectedCarsTab = event.index;
    this.getVehicles(this.carsTabList[this.selectedCarsTab]);
  }


  public tabLocationsChange(event: any) {
    this.selectedLocation = event.index;
    this.getLocations(this.locationsTabList[this.selectedLocation]);
  }


}
