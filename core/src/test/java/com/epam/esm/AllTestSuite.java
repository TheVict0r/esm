package com.epam.esm;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@SelectPackages({"com.epam.esm.service.impl", "com.epam.esm.service.validation.impl", "com.epam.esm.dao.search",
		"com.epam.esm.dto.mapper.impl"})
@Suite
public class AllTestSuite {
}
