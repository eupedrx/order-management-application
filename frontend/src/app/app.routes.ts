import { Routes } from '@angular/router';
import { Products } from './components/layout/products/products';
import { Principal } from './components/layout/principal/principal';
import { Login } from './components/layout/login/login';
import { Productslist } from './components/products/productslist/productslist';
import { Orderslist } from './components/orders/orderslist/orderslist';
import { Productsdetails } from './components/products/productsdetails/productsdetails';
import { Ordersdetails } from './components/orders/ordersdetails/ordersdetails';

export const routes: Routes = [
    {path: '', redirectTo: "login", pathMatch: 'full' },
    {path: "login", component:Login },
    {path: "admin", component: Principal, children: [
        {path: "products", component: Productslist},
        {path: "products/new", component: Productsdetails},
        {path: "orders", component: Orderslist},
        {path: "orders/edit/:id", component: Ordersdetails}

    ]}
];
