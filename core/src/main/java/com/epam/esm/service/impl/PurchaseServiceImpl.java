package com.epam.esm.service.impl;

import com.epam.esm.dao.PurchaseDao;
import com.epam.esm.dao.entity.Purchase;
import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.exception.MismatchedUserAndPurchaseException;
import com.epam.esm.mapper.impl.PurchaseMapperImpl;
import com.epam.esm.service.PurchaseService;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class PurchaseServiceImpl implements PurchaseService {

	private final PurchaseDao purchaseDao;
	private final UserService userService;
	private final PurchaseMapperImpl purchaseMapper;

	@Override
	public PurchaseDto getPurchaseForUser(Long userId, Long purchaseId) {
		log.info("Reading Purchase with ID - {} for the User with ID - {}", purchaseId, userId);

		/* user existence check */
		userService.findById(userId);

		Purchase purchase = purchaseDao.getPurchaseForUser(userId, purchaseId)
				.orElseThrow(() -> new MismatchedUserAndPurchaseException(userId, purchaseId));
		return purchaseMapper.convertToDto(purchase);
	}
}
