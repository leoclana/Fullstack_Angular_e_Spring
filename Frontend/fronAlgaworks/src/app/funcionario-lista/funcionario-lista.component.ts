import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-funcionario-lista',
  templateUrl: './funcionario-lista.component.html',
  styleUrls: ['./funcionario-lista.component.css']
})
export class FuncionarioListaComponent implements OnInit {

  @Input() funcionariosLst: Array<any>;

  constructor() { }

  ngOnInit() {
  }

}
