package com.epam.esm;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

/**
 * Class-runner for all tests except tests for service layer.
 */
@SelectPackages({
        "com.epam.esm.dao.search",
        "com.epam.esm.dao.impl",
        "com.epam.esm.dto.mapper.impl"
})

@Suite
public class CoreTestsSuite {

}