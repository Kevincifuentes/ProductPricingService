package com.inditex.hiring.objectmother.domain;

import com.inditex.hiring.application.offer.AddOfferCommand;
import com.inditex.hiring.domain.Offer;
import com.inditex.hiring.objectmother.InstantiationNotAllowed;

import java.time.Instant;

import static com.inditex.hiring.TestSuiteUtils.FAKER;

public final class OfferMother {

    private OfferMother() throws InstantiationNotAllowed {
        throw new InstantiationNotAllowed(this.getClass().getName());
    }

    public static Offer random() {
        return Offer.builder()
                .brandId(FAKER.random().nextLong())
                .priceListId(FAKER.random().nextLong())
                .startDate(Instant.now())
                .endDate(Instant.now())
                .priority(FAKER.random().nextLong())
                .price(FAKER.random().nextDouble())
                .partNumber(FAKER.commerce().brand())
                .currencyISO(FAKER.currency().code())
                .build();
    }

    public static Offer from(final AddOfferCommand addOfferCommand) {
        return Offer.builder()
                .brandId(addOfferCommand.getBrandId())
                .priceListId(addOfferCommand.getPriceListId())
                .startDate(addOfferCommand.getStartDate())
                .endDate(addOfferCommand.getEndDate())
                .priority(addOfferCommand.getPriority())
                .partNumber(addOfferCommand.getPartNumber())
                .price(addOfferCommand.getPrice())
                .currencyISO(addOfferCommand.getCurrencyISO())
                .build();
    }
}
