import { Component } from '@angular/core';
import { Navbar } from '../navbar/navbar';
import { RouterOutlet } from "@angular/router";

@Component({
  selector: 'app-principal',
  imports: [Navbar, RouterOutlet],
  templateUrl: './principal.html',
  styleUrl: './principal.scss',
})
export class Principal {

}
