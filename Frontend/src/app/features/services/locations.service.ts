import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {Location} from "../modules/content/models/Location";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthService} from "../../authentication/services/auth.service";
import {Observable, of} from "rxjs";
import {StorageService} from "../../authentication/services/storage.service";
import {SearchRequest} from "../modules/content/models/SearchRequest.model";

let apiVersion = `api/v1`;
let endpoint: string = `${environment.backEnd}${apiVersion}`;
let search : string = `${endpoint}/search/locations`;

@Injectable({
    providedIn: 'root'
})
export class LocationsService {

    private httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json',
            "Access-Control-Allow-Origin": "*",
        })
    };

    constructor(private http: HttpClient, private auth: AuthService, private storage: StorageService) {
    }

    public getLocations(page: number = 0): Observable<any> {
        return this.http.get(`${endpoint}/locations?page=${page}`, this.httpOptions);
    }

    public getLocation(id: number): Observable<any> {
        return this.http.get(`${endpoint}/locations/${id}`, this.httpOptions);
    }

    public getCities(page: number = 0): Observable<any> {
        return this.http.get(`${endpoint}/locations/cities?page=${page}`, this.httpOptions);
    }

    public getLocationsByCity(city: string, page: number = 0): Observable<any> {
        return this.http.get(`${endpoint}/locations/cities/${city}?page=${page}`, this.httpOptions);
    }

    public getAvailableVehicles(locationId: number, startDate: string, endDate: string, page: number = 0): Observable<any> {
        return this.http.get(`${endpoint}/locations/${locationId}/available-vehicles?startDate=${startDate}&endDate=${endDate}&page=${page}`, this.httpOptions);
    }

    public searchLocations(searchRequest:SearchRequest, page: number = 0): Observable<any> {
        return this.http.post(`${search}?page=${page}`, searchRequest, this.httpOptions);
    }

}
