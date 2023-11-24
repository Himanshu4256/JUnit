package com.config.repo;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.config.entity.Person;

@SpringBootTest
public class PersonRepoTest {
	@Autowired
	private PersonRepo personRepo;
	
	void isPersonExistsById() {
		Person person = new Person(123l,"Nikhil","Delhi");
		personRepo.save(person);
		
		Boolean actualResult = personRepo.isPersonExitsById(123l);
		assertThat(actualResult).isTrue();
	}
	

}
