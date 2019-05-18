import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-aula97',
  templateUrl: './aula97.component.html',
  styleUrls: ['./aula97.component.css']
})
export class Aula97Component implements OnInit {
  nomeFuncionario;
  sobrenome;

  constructor() {
    this.nomeFuncionario = '';
    this.sobrenome = '';
  }

  ngOnInit() {
  }

  adicionar(sobrenomeInput: string){

    console.log(sobrenomeInput);
    this.sobrenome = sobrenomeInput+". / ";

    // round : aredondar
    // random: randomico, aleatorio
    const numero = Math.round(Math.random() * 100);
    
    //console.log('Adicionar' + this.nomeFuncionario); ou
    //console.log(`Adicionando ${this.nomeFuncionario}`);
    console.log(`Adicionando ${this.nomeFuncionario +' '+this.sobrenome+' - '+ numero}`);
  }

  alterarNome(event: any){
    this.nomeFuncionario = event.target.value;
    //console.log(event);
  }

}
