import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  criador = 'Léo Lana';
  nascimento = 1974;
  dataatual = new Date();

  getIdade(){
    var ano = this.dataatual.getFullYear();
    return ano - this.nascimento;
  }

}