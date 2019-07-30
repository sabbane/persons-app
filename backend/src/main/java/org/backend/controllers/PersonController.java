package org.backend.controllers;

import java.util.List;

import org.backend.entities.Person;
import org.backend.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persons")
public class PersonController {
	@Autowired
	private PersonService personService;

	Logger logger = LoggerFactory.getLogger(PersonController.class);

	@GetMapping("/get-persons")
	public List<Person> createPerson() {
		logger.info("get all persons from the database");
		return this.personService.getAllPersons();
	}

	@PostMapping("/create-person")
	public Person createPerson(@RequestBody Person person) {
		logger.info("create a new person with the name \"" + person.getName() + "\"");
		return this.personService.savePerson(person);
	}

	@PostMapping("/update-person")
	public Person updatePerson(@RequestBody Person person) {
		logger.info("update the data of the person with the id \"" + person.getId() + "\"");
		return this.personService.savePerson(person);
	}

	@PostMapping("/delete-person")
	public void deletePerson(@RequestBody Person person) {
		logger.info("delete the person with the id \"" + person.getId() + "\" and name \"" + person.getName() + "\"");
		this.personService.deletePerson(person);
	}
}
