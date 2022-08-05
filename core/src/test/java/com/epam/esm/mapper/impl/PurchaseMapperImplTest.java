package com.epam.esm.mapper.impl;

import com.epam.esm.dao.entity.Purchase;
import com.epam.esm.dao.entity.User;
import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PurchaseMapperImplTest {

    @Autowired
    PurchaseMapperImpl purchaseMapper;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Test
    void convertToEntityShouldReturnEntity() {
        PurchaseDto purchaseDto = new PurchaseDto(1L, 2L, LocalDateTime.parse("2022-04-25 13:11:03.635", formatter), 99,  null);
        Purchase expectedPurchase = new Purchase(1L, 2L, LocalDateTime.parse("2022-04-25 13:11:03.635", formatter), 99,  null);
        assertEquals(expectedPurchase, purchaseMapper.convertToEntity(purchaseDto));
    }

    @Test
    void convertToDtoShouldReturnDto() {
        Purchase purchase = new Purchase(1L, 2L, LocalDateTime.parse("2022-04-25 13:11:03.635", formatter), 99,  null);
        PurchaseDto expectedPurchaseDto = new PurchaseDto(1L, 2L, LocalDateTime.parse("2022-04-25 13:11:03.635", formatter), 99,  null);
        assertEquals(expectedPurchaseDto, purchaseMapper.convertToDto(purchase));
    }



}