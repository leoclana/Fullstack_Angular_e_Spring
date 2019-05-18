import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-aula913',
  templateUrl: './aula913.component.html',
  styleUrls: ['./aula913.component.css']
})
export class Aula913Component implements OnInit {
  nomeFuncionario;
  sobrenome;
  adicionado = false;
  funcionarios = [];
  ultinoId = 0;

  constructor() {
    this.nomeFuncionario = '';
    this.sobrenome = '';
  }
   
  ngOnInit() {
  }

  adicionar(sobrenomeInput: string){

    this.sobrenome = sobrenomeInput;
    this.adicionado = true;
    const numero = Math.round(Math.random() * 100);
    console.log(`Adicionando ${this.nomeFuncionario +' '+this.sobrenome+' - '+ numero}`);

    this.funcionarios.push({
      id : ++this.ultinoId,
      nome : this.nomeFuncionario +' '+this.sobrenome,
      idade : this.ultinoId + 20
    });
  }

}
