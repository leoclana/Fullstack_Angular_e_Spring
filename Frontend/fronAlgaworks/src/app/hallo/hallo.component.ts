import { Component, Input } from '@angular/core';

@Component({
    selector: 'hallo',
    template: `<h2>
                    Hallo {{nome}}
               </h2>
              `
})
export class HelloComponent{
    //** criando passagem de parametro externo para "nome" com { Input / @Input() }*/
    //nome = 'L.Lana';
    @Input() nome: string;
}