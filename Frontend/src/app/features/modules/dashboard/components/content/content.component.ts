import {Component, OnInit, Output, EventEmitter} from '@angular/core';
import {PaginationModel} from "../../../content/models/pagination.model";
import {CarsService} from "../../../../services/cars.service";
import {Router} from "@angular/router";
import {StorageService} from "../../../../../authentication/services/storage.service";
import {AuthService} from "../../../../../authentication/services/auth.service";
import {LocationsService} from "../../../../services/locations.service";

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.scss']
})
export class ContentComponent implements OnInit {
  public contentType: string = "";
  public content: Array<PaginationModel> = [];
  public totalResults: any;
  public filterType: string = 'all';
  private userId: any;

  constructor(private carsService: CarsService,
              private storage: StorageService,
              private auth: AuthService,
              private locationService: LocationsService,
              private router: Router) {
    this.contentType = this.router.url.split('/')[2];
  }

  ngOnInit() {
    this.userId = this.storage.getUser().id;
    if (this.contentType === 'cars') {
    } else if (this.contentType === 'locations') {
    }
  }

  public changePage(event: any) {
    if (this.contentType === 'cars') {
    } else if (this.contentType === "locations") {
    }
  }

  private filterContentByType(content: Array<PaginationModel>, filter: string) {
    return content;
  }

  public applyFilter(filter: string) {
    this.filterType = filter;
    if (this.contentType.toLowerCase() === 'cars') {
    } else if (this.contentType.toLowerCase() === "locations") {
    }
  }


}
