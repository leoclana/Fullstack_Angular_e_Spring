import { Component } from '@angular/core';

@Component({
    selector: 'hallo',
    template: `<h2>
                    Hallo {{nome}}
               </h2>
              `
})
export class HelloComponent{
    nome = 'L.Lana';
}