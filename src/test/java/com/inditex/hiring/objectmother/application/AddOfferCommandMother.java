package com.inditex.hiring.objectmother.application;

import com.inditex.hiring.application.offer.AddOfferCommand;
import com.inditex.hiring.objectmother.InstantiationNotAllowed;

import java.time.Instant;

import static com.inditex.hiring.TestSuiteUtils.FAKER;

public final class AddOfferCommandMother {
    private AddOfferCommandMother() throws InstantiationNotAllowed {
        throw new InstantiationNotAllowed(this.getClass().getName());
    }

    public static AddOfferCommand random() {
        return AddOfferCommand.builder()
                .brandId(FAKER.random().nextLong())
                .productId(FAKER.random().nextLong())
                .priceListId(FAKER.random().nextLong())
                .startDate(Instant.now())
                .endDate(Instant.now())
                .priority(FAKER.random().nextLong())
                .price(FAKER.random().nextDouble())
                .partNumber(FAKER.commerce().brand())
                .currencyISO(FAKER.currency().code())
                .build();
    }
}
