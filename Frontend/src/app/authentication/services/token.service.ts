import { Injectable } from '@angular/core';
import jwt_decode from "jwt-decode";

const ACCESS = 'access_token';
@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }

  public getAccessToken(): string | null {
    return localStorage.getItem(ACCESS);
  }

  public removeAccessToken(): boolean {
    if (!localStorage.getItem(ACCESS)) {
      return false;
    }

    localStorage.removeItem(ACCESS);
    return true;
  }

  public saveToken(token: string): void {
    localStorage.removeItem(ACCESS);
    localStorage.setItem(ACCESS, token);
  }

  public createToken(access: string): void {
    localStorage.setItem(ACCESS, access);
  }

  public getDecodedAccessToken(token: string): any {
    try {
      return jwt_decode(token);
    } catch (Error) {
      return null;
    }
  }
}
