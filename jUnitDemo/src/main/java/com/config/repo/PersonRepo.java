package com.config.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.config.entity.Person;

public interface PersonRepo extends JpaRepository<Person, Long>{

	Boolean isPersonExitsById(Long i);
	
}
