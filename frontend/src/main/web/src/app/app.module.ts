import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PersonComponent } from './components/person/person.component';
import { FormsModule } from '@angular/forms';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faEdit, faTrash, faCheck, faBan } from '@fortawesome/free-solid-svg-icons';


@NgModule({
    declarations: [
        AppComponent,
        PersonComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        FontAwesomeModule,
        HttpClientModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
    constructor() {
        // Add an icon to the library for convenient access in other components
        library.add(faEdit, faTrash, faCheck, faBan);
    }
}
