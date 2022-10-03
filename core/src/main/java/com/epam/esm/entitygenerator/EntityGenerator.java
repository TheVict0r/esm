package com.epam.esm.entitygenerator;

import org.springframework.stereotype.Service;

/**
 * Entities generator for this task:
 *
 * Generate for a demo at least 1000 users 1000 tags 10’000 gift certificates
 * (should be linked with tags and users) All values should look like more
 * -or-less meaningful: random words, but not random letters
 *
 * For private use only
 */
@Service
public interface EntityGenerator {

	public void generateEntities();
}
