import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Bundle } from '../app/bundle';
import { Product } from './company-stats/models/product';

@Injectable({
  providedIn: 'root',
})
export class BundleService {
  private baseURL = 'http://localhost:9095/bundles';
  token = sessionStorage.getItem('token') as string | undefined;

  httpOptions = {
    headers: new HttpHeaders({
    'Authorization': 'Bearer ' + this.token
    })
  };

  constructor(private http: HttpClient) {}

  getAllBundles(): Observable<Bundle[]> {
    return this.http.get<Bundle[]>(`${this.baseURL}`);
  }

  getBundleById(id: string): Observable<Bundle> {
    return this.http.get<Bundle>(`${this.baseURL}/view/${id}`);
  }

  viewBundleByCompanyId(companyId: string, id: string): Observable<Bundle> {
    return this.http.get<Bundle>(`${this.baseURL}/${companyId}/${id}`);
  }

  createBundle(bundle: Bundle): Observable<Bundle> {
    return this.http.post<Bundle>(`${this.baseURL}/create`, bundle);
  }

  updateBundle(id: string, bundle: Bundle): Observable<Bundle> {
    return this.http.put<Bundle>(`${this.baseURL}/update/${id}`, bundle);
  }

  deleteBundle(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseURL}/delete/${id}`);
  }

  getAllProductsByCompanyID(companyID: string): Observable<Array<Product>>{    
      return this.http.get<Array<Product>>(`http://localhost:9093/products/view/products-by-company/${companyID}`, this.httpOptions)
  }
}
