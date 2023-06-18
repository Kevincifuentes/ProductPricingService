package com.inditex.hiring.objectmother.application;

import com.inditex.hiring.application.offer.AddOfferCommand;
import com.inditex.hiring.objectmother.InstantiationNotAllowed;

import java.math.BigDecimal;
import java.time.Instant;

import static com.inditex.hiring.TestSuiteUtils.FAKER;
import static com.inditex.hiring.objectmother.infrastructure.persistence.jpa.read.model.OfferViewMother.*;

public final class AddOfferCommandMother {
    private AddOfferCommandMother() throws InstantiationNotAllowed {
        throw new InstantiationNotAllowed(this.getClass().getName());
    }

    public static AddOfferCommand random() {
        return AddOfferCommand.builder()
                .brandId(FAKER.random().nextInt())
                .priceListId(FAKER.random().nextLong())
                .startDate(Instant.now())
                .endDate(Instant.now())
                .priority(FAKER.random().nextInt())
                .price(BigDecimal.valueOf(
                        FAKER.number().randomDouble(MAX_NUMBER_OF_DECIMALS_PRICE, MIN_VALUE_PRICE, MAX_VALUE_PRICE))
                )
                .partNumber(FAKER.commerce().brand())
                .currencyISO(FAKER.currency().code())
                .build();
    }
}
