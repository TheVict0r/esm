package com.epam.esm.controller;

import com.epam.esm.entitygenerator.EntityGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Very secret entities generator for this task:
 *
 * Generate for a demo at least 1000 users 1000 tags 10’000 gift certificates
 * (should be linked with tags and users) All values should look like more
 * -or-less meaningful: random words, but not random letters
 *
 * For private use only :)
 */
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/generator")
public class EntityGeneratorController {

	private final EntityGenerator generator;

	@GetMapping(value = "/iddqd")
	public String generateEntities() {
		generator.generateEntities();
		return "Ready. Check database.";
	}

}
