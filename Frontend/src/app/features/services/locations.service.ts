import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {Location} from "../modules/content/models/Location";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthService} from "../../authentication/services/auth.service";
import {Observable, of} from "rxjs";
import {StorageService} from "../../authentication/services/storage.service";

@Injectable({
  providedIn: 'root'
})
export class LocationsService {
  private endpoint: string = `${environment.backEnd}api/v1/`;

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      "Access-Control-Allow-Origin": "*",
    })
  };

  constructor(private http: HttpClient, private auth: AuthService, private storage: StorageService) { }

  public getLocations(page:number = 0): Observable<any> {
    return this.http.get(`${this.endpoint}locations?page=${page}`, this.httpOptions);
  }
  public getLocation(id: number): Observable<any> {
    return this.http.get(`${this.endpoint}locations/${id}`, this.httpOptions);
  }
  public getCities(page:number = 0): Observable<any> {
    return this.http.get(`${this.endpoint}locations/cities?page=${page}`, this.httpOptions);
  }

  public getLocationsByCity(city: string, page:number = 0): Observable<any> {
    return this.http.get(`${this.endpoint}locations/cities/${city}?page=${page}`, this.httpOptions);
  }

}
