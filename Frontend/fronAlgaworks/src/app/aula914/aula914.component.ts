import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-aula914',
  templateUrl: './aula914.component.html',
  styleUrls: ['./aula914.component.css']
})
export class Aula914Component implements OnInit {
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
