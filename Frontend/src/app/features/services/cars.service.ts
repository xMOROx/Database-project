import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthService} from "src/app/authentication/services/auth.service";
import {environment} from "src/environments/environment";
import {CarModel} from "src/app/features/modules/content/models/Car.model";
import {Observable, of} from "rxjs";
import {StorageService} from "../../authentication/services/storage.service";

@Injectable({
  providedIn: 'root'
})
export class CarsService {
  private endpoint: string = `${environment.backEnd}api/v1/`;

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      "Access-Control-Allow-Origin": "*",
    })
  };

  public parseCar(car: any): CarModel {
    return {
      id: car.id,
      vehicleParametersId: car.vehicleParametersId,
      registration: car.registration,
      brand: car.brand,
      model: car.model,
      dailyFee: car.dailyFee,
      bestOffer: car.bestOffer
    } as CarModel;
  }

  constructor(private http: HttpClient, private auth: AuthService, private storage: StorageService) {
  }

  // general cars
  public getVehicleById(id: any): Observable<any> {
    return this.http.get(`${this.endpoint}vehicles/${id}`, this.httpOptions);
  }

  public getBestOfferVehicles(page: number): Observable<any> {
    return this.http.get(`${this.endpoint}best-offers?page=${page}`, this.httpOptions);
  }

  public getVehicles(page: number): Observable<any> {
    return this.http.get(`${this.endpoint}vehicles?page=${page}`, this.httpOptions);
  }

  public getVehicleParameters(id: any): Observable<any> {
    return this.http.get(`${this.endpoint}vehicles/${id}/parameters`, this.httpOptions);
  }

}
