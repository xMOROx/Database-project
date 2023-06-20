import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {AuthService} from "../../authentication/services/auth.service";
import {StorageService} from "../../authentication/services/storage.service";

let apiVersion = `api/v1`;
let endpoint: string = `${environment.backEnd}${apiVersion}/bookings`;

@Injectable({
    providedIn: 'root'
})
export class BookingsService {

  constructor(private http: HttpClient, private auth: AuthService, private storage: StorageService) {
  }

    private httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json',
            "Access-Control-Allow-Origin": "*",
        })
    };

    public reserveVehicle(data: any): any {
        return this.http.post(`${endpoint}/reserve`, data, this.httpOptions);
    }
}
