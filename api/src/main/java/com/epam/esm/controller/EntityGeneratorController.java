package com.epam.esm.controller;

import com.epam.esm.service.CertificateService;
import com.epam.esm.service.EntityGeneratorService;
import com.epam.esm.service.impl.EntityGeneratorServiceImpl;
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

    private final EntityGeneratorService generatorService;

    @GetMapping(value = "/iddqd")
    public String generateEntities(){
        generatorService.generateEntities();
        return "Ready. Check database.";
    }

}
