import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';

@Component({
  selector: 'app-login',
  imports: [MdbFormsModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {


  username: string = '';
  password: string = '';

  router = inject(Router);

  logar() {

    if(this.username === 'admin' && this.password === 'admin') {
      alert('Login realizado com sucesso!');
      this.router.navigate(['admin/products']);
    } else {
      alert('Usuário ou senha incorretos.');
    }


}
}
