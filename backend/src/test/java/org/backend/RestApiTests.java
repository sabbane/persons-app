package org.backend;

import static org.junit.Assert.fail;

import org.backend.entities.Person;
import org.backend.repositories.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class RestApiTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private CommonMethods commonMethods;

	private String controllerUrl;

	@Before
	public void init() {
		controllerUrl = "http://localhost:" + port + "/persons";
	}

	@Test
	public void getPersons() {
		Person person_1 = new Person();
		person_1.setName("JunitUser 1");
		commonMethods.persistNewPerson(person_1);

		Person person_2 = new Person();
		person_2.setName("JunitUser 2");
		commonMethods.persistNewPerson(person_2);

		Person[] persons = this.testRestTemplate.getForObject(controllerUrl + "/get-persons", Person[].class);

		if (persons.length != 2) {
			fail("Could not get the persons from the database");
		}
	}

	@Test
	public void createPerson() {
		Person person = new Person();
		person.setName("JunitUser");

		System.out.println(person.getId());

		ResponseEntity<Person> persistedPerson = this.testRestTemplate.postForEntity(controllerUrl + "/create-person",
				person, Person.class);

		if (persistedPerson == null || persistedPerson.getBody().getId() < 1) {
			fail("Could not save person into the database");
		}

		this.personRepository.delete(persistedPerson.getBody());
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

		this.testRestTemplate.postForEntity(controllerUrl + "/update-person", persistedPerson, Person.class);

		Person updatedPerson = commonMethods.checkUpdatedPerson(persistedPerson, newName);

		this.personRepository.delete(updatedPerson);
	}

	@Test
	public void deletePerson() {
		Person person = new Person();
		person.setName("JunitUser");
		commonMethods.persistNewPerson(person);

		Person persistedPerson = commonMethods.findPersonByName(person.getName());

		this.testRestTemplate.postForEntity(controllerUrl + "/delete-person", persistedPerson, Person.class);

		commonMethods.checkDeletedPerson(persistedPerson.getName());
	}

}
