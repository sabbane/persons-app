import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Person } from 'src/app/models/person';
import { PersonService } from 'src/app/services/person.service';

@Component({
    selector: 'app-person',
    templateUrl: './person.component.html',
    styleUrls: ['./person.component.css']
})

export class PersonComponent implements OnInit {
    editMode = false;
    newPerson: Person;

    registeredPersons: Person[] = new Array();
    editedNames = new Map();

    constructor(private personService: PersonService) { }

    ngOnInit() {
    }

    createPerson() {
        this.editMode = true;
    }

    editPerson(personIndex: number) {
        this.editedNames.set(personIndex, this.registeredPersons[personIndex].name);
        this.registeredPersons[personIndex].editMode = true;
    }

    cancelEditPerson(personIndex: number) {
        if (this.editedNames.get(personIndex)) {
            this.registeredPersons[personIndex].name = this.editedNames.get(personIndex);
        }
        this.registeredPersons[personIndex].editMode = false;
    }

    onSubmit(form: NgForm) {
        this.editMode = false;
        this.newPerson = new Person(form.value.name);
        this.personService.createPerson(this.newPerson).subscribe(
            (data: Person) => this.registeredPersons.push(data)
        );
    }

    updatePerson(personIndex: number) {
        this.registeredPersons[personIndex].editMode = false;

        this.personService.updatePerson(this.registeredPersons[personIndex]).subscribe(
            (data: Person) => this.registeredPersons[personIndex] = data
        );
    }

    deletePerson(personIndex: number) {
        this.personService.deletePerson(this.registeredPersons[personIndex]).subscribe(
            () => this.registeredPersons.splice(personIndex, 1)
        );
    }
}
