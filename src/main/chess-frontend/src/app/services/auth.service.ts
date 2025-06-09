import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {catchError, map, Observable, throwError} from 'rxjs';
import {Credentials} from '../model/Credentials';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private credentials: Credentials | null = null;
  errorMessage: string = '';
  displayError: boolean = false;

  constructor(private http: HttpClient) {
  }

  setCredentials(username: string | null, password: string | null) {
    this.credentials = {username, password};
  }

  isLoggedIn(): boolean {
    return this.credentials !== null;
  }

  login() {
    return this.get("http://localhost:8080/v1/auth/validate").pipe(
      map(() => true),
      catchError(err => {
        return throwError(() => err);
      })
    )
  }

  logout() {
    this.setCredentials(null, null);
  }

  get<T>(url: string): Observable<T> {
    return this.http.get<T>(url, {headers: this.getHeaders()});
  }

  post<T, B = any>(url: string, body: B): Observable<T> {
    return this.http.post<T>(url, body, {
      headers: this.getHeaders()
    });
  }

  put<T, B = any>(url: string, body: B): Observable<T> {
    return this.http.put<T>(url, body, {
      headers: this.getHeaders()
    });
  }

  delete<T>(url: string): Observable<T> {
    return this.http.delete<T>(url, {headers: this.getHeaders()});
  }

  private getHeaders(): HttpHeaders {
    if (!this.credentials) throw new Error("No credentials set");
    const {username, password} = this.credentials;
    const token = btoa(`${username}:${password}`);
    return new HttpHeaders({'Authorization': `Basic ${token}`});
  }

  // @ts-ignore
  setErrorMessage(err) {
    return this.errorMessage = err.error?.message || 'An unexpected error has occurred. Please try again later.';
  }
}
