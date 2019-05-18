import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-aula912',
  templateUrl: './aula912.component.html',
  styleUrls: ['./aula912.component.css']
})
export class Aula912Component implements OnInit {
  nomeFuncionario;
  sobrenome;
  adicionado = false;

  constructor() {
    this.nomeFuncionario = '';
    this.sobrenome = '';
  }

  ngOnInit() {
  }

  adicionar(sobrenomeInput: string){

    this.sobrenome = sobrenomeInput+". / ";
    this.adicionado = true;
    const numero = Math.round(Math.random() * 100);
    console.log(`Adicionando ${this.nomeFuncionario +' '+this.sobrenome+' - '+ numero}`);
  }


}
