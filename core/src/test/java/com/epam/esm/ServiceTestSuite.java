package com.epam.esm;

/**
 * Class-runner for all service layer unit tests.
 */

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@SelectPackages({"com.epam.esm.service.impl",
        "com.epam.esm.service.validation.impl"
        })

@Suite
public class ServiceTestSuite {

}