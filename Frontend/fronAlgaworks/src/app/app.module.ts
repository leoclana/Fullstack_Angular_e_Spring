import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HelloComponent } from './hallo/hallo.component';
import { BemVindoComponent } from './bem-vindo/bem-vindo.component';
import { Aula97Component } from './aula97/aula97.component';
import { Aula912Component } from './aula912/aula912.component';
import { Aula913Component } from './aula913/aula913.component';
import { Aula914Component } from './aula914/aula914.component';
import { FuncionarioCardComponent } from './funcionario-card/funcionario-card.component';

@NgModule({
  declarations: [
    AppComponent,
    HelloComponent,
    BemVindoComponent,
    Aula97Component,
    Aula912Component,
    Aula913Component,
    Aula914Component,
    FuncionarioCardComponent
  ],
  imports: [
    BrowserModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
