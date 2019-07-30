package org.backend.services;

import java.util.List;

import org.backend.entities.Person;
import org.backend.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
	@Autowired
	private PersonRepository personRepository;

	public List<Person> getAllPersons() {
		return this.personRepository.findAll();
	}

	public Person savePerson(Person person) {
		Person persistedPerson = this.personRepository.save(person);
		return persistedPerson;
	}

	public void deletePerson(Person person) {
		this.personRepository.delete(person);
	}
}
