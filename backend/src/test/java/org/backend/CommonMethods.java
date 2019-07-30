package org.backend;

import static org.junit.Assert.fail;

import org.backend.entities.Person;
import org.backend.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonMethods {
	@Autowired
	private PersonRepository personRepository;

	public Person persistNewPerson(Person person) {
		if (this.personRepository.findByName(person.getName()) != null) {
			fail("The JunitPerson is already persisted in the database. Please remove it first");
		}

		return this.personRepository.save(person);
	}

	public Person checkUpdatedPerson(Person persistedPerson, String newName) {
		Person updatedPerson = this.personRepository.findByName(newName);
		if (updatedPerson == null || updatedPerson.getId() != persistedPerson.getId()) {
			fail("Could not update person in the database");
		}
		return updatedPerson;
	}

	public Person findPersonByName(String name) {
		Person persistedPerson = this.personRepository.findByName(name);
		if (persistedPerson == null) {
			fail("Could not save person into the database");
		}
		return persistedPerson;
	}

	public void checkDeletedPerson(String name) {
		Person deletedPerson = this.personRepository.findByName(name);
		if (deletedPerson != null) {
			fail("Could not delete person from the database");
		}
	}
}
