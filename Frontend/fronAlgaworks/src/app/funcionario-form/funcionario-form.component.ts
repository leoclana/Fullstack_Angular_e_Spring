import { Component, OnInit, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-funcionario-form',
  templateUrl: './funcionario-form.component.html',
  styleUrls: ['./funcionario-form.component.css']
})
export class FuncionarioFormComponent implements OnInit {
  nomeFuncionario;
  sobrenome;
  adicionado = false;
  ultinoId = 0;

  @Output() funcionarioAdicionado = new EventEmitter();
  // Tambem aceita alias :  @Output('nome exposto') funcionarioAdicionado = new EventEmitter();

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

    const funcionario = {
      id: ++this.ultinoId,
      nome: this.nomeFuncionario +' '+this.sobrenome,
      idade: this.ultinoId + 20
    };
    //## @Output() - Emitir o metodo para passar o "funcionario" para tela que chamou <app-fincionario-form...>
    //Ps. o funcionario sera lido no html-chamador como um evento($event) Ex.: <app-fincionario-form (funcionarioAdicionado)="aoAdicionar($event)"></app-fincionario-form>
    this.funcionarioAdicionado.emit(funcionario);
  }

}
