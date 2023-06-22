import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthService} from "src/app/authentication/services/auth.service";
import {environment} from "src/environments/environment";
import {Observable, of} from "rxjs";
import {StorageService} from "../../authentication/services/storage.service";
import {SearchRequest} from "../modules/content/models/SearchRequest.model";

let apiVersion = `api/v1`;
let endpoint: string = `${environment.backEnd}${apiVersion}`;
let search : string = `${environment.backEnd}${apiVersion}/search/vehicles`;
@Injectable({
    providedIn: 'root'
})
export class CarsService {

    private httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json',
            "Access-Control-Allow-Origin": "*",
        })
    };

    constructor(private http: HttpClient, private auth: AuthService, private storage: StorageService) {
    }

    // general cars
    public getVehicleById(id: any): Observable<any> {
        return this.http.get(`${endpoint}/vehicles/${id}`, this.httpOptions);
    }

    public getBestOfferVehicles(page: number): Observable<any> {
        return this.http.get(`${endpoint}/best-offers?page=${page}`, this.httpOptions);
    }

    public getVehicles(page: number): Observable<any> {
        return this.http.get(`${endpoint}/vehicles?page=${page}`, this.httpOptions);
    }

    public getLocationForVehicle(id: string): Observable<any> {
        return this.http.get(`${endpoint}/vehicles/${id}/location`, this.httpOptions);
    }

    //   search

    public getVehicleModels(brand:string | null = null, page:number = 0): Observable<any> {
        return this.http.get(`${search}/models?page=${page}`, this.httpOptions);
    }

    public getVehicleBrands(page:number = 0): Observable<any> {
        return this.http.get(`${search}/brands?page=${page}`, this.httpOptions);
    }

    public getVehicleBodyTypes(page:number = 0): Observable<any> {
        return this.http.get(`${search}/body-types?page=${page}`, this.httpOptions);
    }

    public getVehicleColors(page:number = 0): Observable<any> {
        return this.http.get(`${search}/colors?page=${page}`, this.httpOptions);
    }

    public searchVehicles(searchRequest: SearchRequest): Observable<any> {
        console.log(searchRequest);
        return this.http.post(`${search}`, searchRequest, this.httpOptions);
    }
}
