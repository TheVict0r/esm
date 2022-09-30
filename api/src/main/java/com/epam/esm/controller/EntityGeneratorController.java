package com.epam.esm.controller;

import com.epam.esm.entitygenerator.EntityGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Entities generator for this task:
 *
 * Generate for a demo at least 1000 users 1000 tags 10’000 gift certificates
 * (should be linked with tags and users) All values should look like more
 * -or-less meaningful: random words, but not random letters
 *
 * Not a part of API. For private use only
 */
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/generator")
public class EntityGeneratorController {

	private final EntityGenerator generator;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void generateEntities() {
		generator.generateEntities();
	}

}
