package com.epam.esm.controller;

import com.epam.esm.entitygenerator.EntityGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/generator")
public class EntityGeneratorController {

	private final EntityGenerator generatorService;

	@GetMapping(value = "/iddqd")
	public String generateEntities() {
		generatorService.generateEntities();
		return "Ready. Check database.";
	}

}
