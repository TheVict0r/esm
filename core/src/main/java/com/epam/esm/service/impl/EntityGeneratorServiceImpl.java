package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.EntityGeneratorService;
import com.epam.esm.service.PurchaseService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import java.util.Random;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EntityGeneratorServiceImpl implements EntityGeneratorService {

	private final TagService tagService;
	private final CertificateService certificateService;
	private final PurchaseService purchaseService;
	private final UserService userService;

	@Override
	public void generateEntities() {
		 generateCertificatesWithTags();
		// generateUsers();
		// addPurchasesToUsers();
	}

	private void generateCertificatesWithTags() {
		// for (int i = 1; i <= 1_004; i++){
		// for (int i = 5_990; i <= 6_000; i++){
		int counterTo1000 = 0;
		for (int i = 9_990; i <= 10_000; i++) {
			counterTo1000++;
			if(counterTo1000 == 1001){
				counterTo1000 = 1;
			}

			TagDto tag = new TagDto(null, "Tag name " + counterTo1000);

			Random random = new Random();
			int randomPrice = random.nextInt(50) + 1;
			int randomDuration = random.nextInt(70) + 20;
			CertificateDto certificateDto = new CertificateDto(null, "Certificate name " + i, "Description " + i,
					randomPrice, randomDuration, null, null, Set.of(tag));

			System.out.println(certificateDto);

		}
	}

	// /* OT GPEXA PODALSHE :) */
	// private void generateUsers(){
	//
	// for (int i = 1; i <= 1000; i++){
	// UserDto userDto = new UserDto(null, "User " + i, null);
	// userService.create(userDto);
	// }
	//
	// }

	private void addPurchasesToUsers() {

		for (int i = 1; i <= 1000; i++) {

		}

	}

}
