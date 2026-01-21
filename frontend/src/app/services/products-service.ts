import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProductFilterModel } from '../models/products/product-filter.model';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root',
})
export class ProductsService {
  
    http = inject(HttpClient);

    API = "http://localhost:8080/api/products"


    listarProdutos(): Observable<ProductFilterModel[]> {
      return this.http.get<ProductFilterModel[]>(this.API);
    }

}
