package org.backend;

import static org.junit.Assert.fail;

import org.backend.entities.Person;
import org.backend.repositories.PersonRepository;
import org.backend.services.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PersonServiceTests {
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private PersonService personService;
	@Autowired
	private CommonMethods commonMethods;

	@Test
	public void createPerson() {
		Person person = new Person();
		person.setName("JunitUser");

		commonMethods.persistNewPerson(person);

		Person persistedPerson = this.personService.savePerson(person);

		if (persistedPerson.getId() < 1) {
			fail("Could not save person into the database");
		}

		this.personRepository.delete(persistedPerson);
	}

	@Test
	public void updatePerson() {
		// Add new person entry into the database
		Person person = new Person();
		person.setName("JunitUser");
		Person persistedPerson = commonMethods.persistNewPerson(person);

		// Update the name of the persisted person entry
		String newName = "Mustermann";
		persistedPerson.setName(newName);
		this.personService.savePerson(persistedPerson);

		Person updatedPerson = commonMethods.checkUpdatedPerson(persistedPerson, newName);

		this.personRepository.delete(updatedPerson);
	}

	@Test
	public void deletePerson() {
		Person person = new Person();
		person.setName("JunitUser");

		commonMethods.persistNewPerson(person);

		Person persistedPerson = commonMethods.findPersonByName(person.getName());

		this.personService.deletePerson(persistedPerson);

		commonMethods.checkDeletedPerson(person.getName());
	}
}
