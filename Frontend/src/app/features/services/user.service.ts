import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {StorageService} from "../../authentication/services/storage.service";
import {HttpClient, HttpHeaders} from "@angular/common/http";

let apiVersion = `api/v1`;
let endpoint: string = `${environment.backEnd}${apiVersion}/users`;

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private storage: StorageService) { }

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      "Access-Control-Allow-Origin": "*",
      Authorization: "Bearer " + localStorage.getItem('access_token')
    })
  };

  public getBookingsForUser(user: any): any {
    return this.http.get(`${endpoint}/${user}/bookings`, this.httpOptions);
  }
}
