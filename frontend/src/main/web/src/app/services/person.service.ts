import { Injectable } from '@angular/core';
import { Person } from '../models/person';
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class PersonService {

    constructor(private httpClient: HttpClient) { }

    getPersons() {
        const url = '/persons/get-persons';
        return this.httpClient.get(url);
    }

    createPerson(person: Person) {
        const url = '/persons/create-person';
        return this.httpClient.post<Person>(url, person);
    }

    updatePerson(person: Person) {
        const url = '/persons/update-person';
        return this.httpClient.post<Person>(url, person);
    }

    deletePerson(person: Person) {
        const url = '/persons/delete-person';
        return this.httpClient.post<Person>(url, person);
    }
}
