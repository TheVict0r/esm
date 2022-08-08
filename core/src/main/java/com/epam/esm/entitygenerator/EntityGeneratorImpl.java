package com.epam.esm.entitygenerator;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.PurchaseService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import java.util.Random;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Very secret entities generator for this task:
 *
 * Generate for a demo at least 1000 users 1000 tags 10’000 gift certificates
 * (should be linked with tags and users) All values should look like more
 * -or-less meaningful: random words, but not random letters
 *
 * For private use only :)
 */
@RequiredArgsConstructor
@Service
public class EntityGeneratorImpl implements EntityGenerator {

	private final TagService tagService;
	private final CertificateService certificateService;
	private final PurchaseService purchaseService;
	private final UserService userService;
	private String dataBaseName = " dev";
	// private String dataBaseName = " prod";

	private final Random random = new Random();

	@Override
	public void generateEntities() {
		/* use by 1 method only - OT GPEXA PODALSHE :) */

		// generateCertificatesWithTags();
		// generateUsers();
		// addPurchasesToUsers();
	}

	private void generateCertificatesWithTags() {
		int counterTo1000 = 0;
		for (int i = 1; i <= 10_000; i++) {
			counterTo1000++;
			if (counterTo1000 == 1001) {
				counterTo1000 = 1;
			}

			TagDto tag = new TagDto(null, "Tag " + counterTo1000 + dataBaseName);

			int randomPrice = random.nextInt(50) + 1;
			int randomDuration = random.nextInt(70) + 20;
			CertificateDto certificateDto = new CertificateDto(null, "name " + i + dataBaseName, "description " + i,
					randomPrice, randomDuration, null, null, Set.of(tag));

			certificateService.create(certificateDto);
		}
	}

	private void generateUsers() {

		for (int i = 1; i <= 1000; i++) {
			UserDto userDto = new UserDto(null, "User " + i, null);
			userService.create(userDto);
		}

	}

	private void addPurchasesToUsers() {

		for (long i = 1; i <= 1000; i++) {
			PurchaseDto purchaseDto = new PurchaseDto();
			purchaseDto.setUserId(i);

			long certificateId = random.nextLong(10_000) + 1;
			CertificateDto certificateDto = CertificateDto.builder().id(certificateId).build();
			purchaseDto.setCertificates(Set.of(certificateDto));

			purchaseService.create(i, purchaseDto);
		}

	}

}
