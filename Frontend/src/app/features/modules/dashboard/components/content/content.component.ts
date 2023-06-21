import {Component, OnInit, Output, EventEmitter} from '@angular/core';
import {PaginationModel} from "../../../content/models/pagination.model";
import {CarsService} from "../../../../services/cars.service";
import {Router} from "@angular/router";
import {StorageService} from "../../../../../authentication/services/storage.service";
import {AuthService} from "../../../../../authentication/services/auth.service";
import {LocationsService} from "../../../../services/locations.service";
import {UserService} from "../../../../services/user.service";

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.scss']
})
export class ContentComponent implements OnInit {
  public contentType: string = "";
  public content?: any;
  public totalResults: any;
  public filterType: string = 'all';
  private userId: any;

  constructor(private carsService: CarsService,
              private storage: StorageService,
              private auth: AuthService,
              private locationService: LocationsService,
              private router: Router,
              private userService: UserService) {
    this.contentType = this.router.url.split('/')[2];
  }

  ngOnInit() {
    this.userId = this.storage.getUser().id;
    if (this.contentType === 'bookings') {
      this.userService.getBookingsForUser(this.userId).subscribe((res: any) => {
        this.content = res;
        console.log(this.content);
        // this.content.results = res._embedded.Bookings;
        // this.content.page = res.page.number;
        // this.content.total_pages = res.page.totalPages;
        // this.content.total_results = res.page.totalElements;
        this.totalResults = res.page.totalElements;
      });
    } else if (this.contentType === 'locations') {
    }
  }

  public changePage(event: any) {
    if (this.contentType === 'bookings') {
      this.userService.getBookingsForUser(this.userId).subscribe((res: any) => {
        this.content = res;
        console.log(this.content);
        // this.content.results = res._embedded.Bookings;
        // console.log(this.content.__embedded.Bookings);
        // this.content.page = res.page.number;
        // this.content.total_pages = res.page.totalPages;
        // this.content.total_results = res.page.totalElements;
        this.totalResults = res.page.totalElements;
      });
    } else if (this.contentType === "locations") {
    }
  }

  private filterContentByType(content: Array<PaginationModel>, filter: string) {
    return content;
  }

  public applyFilter(filter: string) {
    this.filterType = filter;
    if (this.contentType.toLowerCase() === 'bookings') {
      this.userService.getBookingsForUser(this.userId).subscribe((res: any) => {
        this.content = res;
        console.log(this.content);
        // this.content.results = res._embedded.Bookings;
        // console.log(this.content.__embedded.Bookings);
        // this.content.page = res.page.number;
        // this.content.total_pages = res.page.totalPages;
        // this.content.total_results = res.page.totalElements;
        this.totalResults = res.page.totalElements;
      });
    } else if (this.contentType.toLowerCase() === "locations") {
    }
  }

}
