package com.epam.esm.service.impl;

import com.epam.esm.dao.PurchaseDao;
import com.epam.esm.dao.entity.Purchase;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.exception.MismatchedUserAndPurchaseException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.PurchaseMapperImpl;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.PurchaseService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.validation.InputDataValidator;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class PurchaseServiceImpl implements PurchaseService {
	private final PurchaseDao purchaseDao;
	private final UserService userService;
	private final CertificateService certificateService;
	private final PurchaseMapperImpl purchaseMapper;
	private final InputDataValidator validator;

	@Override
	public PurchaseDto getPurchaseForUser(Long userId, Long purchaseId) {
		log.info("Reading Purchase with ID - {} for the User with ID - {}", purchaseId, userId);

		/* user existence check */
		userService.getById(userId);

		Purchase purchase = purchaseDao.getPurchaseForUser(userId, purchaseId)
				.orElseThrow(() -> new MismatchedUserAndPurchaseException(userId, purchaseId));
		return purchaseMapper.convertToDto(purchase);
	}

	@Override
	public PurchaseDto create(Long userId, PurchaseDto purchaseDto) {
		log.debug("Adding PurchaseDto - {} to user with ID - {}", purchaseDto, userId);
		validator.pathAndBodyIdsCheck(userId, purchaseDto.getUserId());
		Purchase purchase;
		if (userService.isUserExist(userId)) {
			purchaseDto.setDate(LocalDateTime.now());

			Set<CertificateDto> certificatesIdOnly = purchaseDto.getCertificates();
			Set<CertificateDto> certificatesFullData = new HashSet<>();
			int purchaseCost = 0;
			for (CertificateDto certificateDtoIdOnly : certificatesIdOnly) {
				CertificateDto certificateDtoFullData = certificateService.getById(certificateDtoIdOnly.getId());
				certificatesFullData.add(certificateDtoFullData);
				purchaseCost += certificateDtoFullData.getPrice();
			}
			purchaseDto.setCost(purchaseCost);
			purchaseDto.setCertificates(certificatesFullData);
			purchase = purchaseDao.create(purchaseMapper.convertToEntity(purchaseDto));
		} else {
			throw new ResourceNotFoundException(userId);
		}
		return purchaseMapper.convertToDto(purchase);
	}

}