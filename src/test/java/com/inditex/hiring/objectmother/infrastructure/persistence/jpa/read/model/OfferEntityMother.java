package com.inditex.hiring.objectmother.infrastructure.persistence.jpa.read.model;

import com.inditex.hiring.domain.Offer;
import com.inditex.hiring.infrastructure.persistence.jpa.write.OfferEntity;
import com.inditex.hiring.objectmother.InstantiationNotAllowed;
import com.inditex.hiring.objectmother.domain.OfferMother;

import java.time.temporal.ChronoUnit;

import static com.inditex.hiring.TestSuiteUtils.FAKER;

public class OfferEntityMother {
    private final static int ONE = 1;

    private OfferEntityMother() throws InstantiationNotAllowed {
        throw new InstantiationNotAllowed(this.getClass().getName());
    }

    public static OfferEntity random() {
        return from(OfferMother.random());
    }

    public static OfferEntity from(final Offer offer) {
        final var entity = new OfferEntity();
        entity.setId(offer.getId());
        entity.setBrandId(offer.getBrandId());
        entity.setPriceListId(offer.getPriceListId());
        entity.setStartDate(offer.getStartDate());
        entity.setEndDate(offer.getEndDate());
        entity.setPriority(offer.getPriority());
        entity.setPartNumber(offer.getPartNumber());
        entity.setPrice(offer.getPrice());
        entity.setCurrencyISO(offer.getCurrencyISO());
        return entity;
    }

    public static OfferEntity before(final OfferEntity firstEntity) {
        final var startDate = firstEntity.getStartDate().plus(ONE, ChronoUnit.DAYS);
        final var endDate = startDate.plus(ONE, ChronoUnit.HOURS);
        final var newEntity = new OfferEntity();
        newEntity.setId(FAKER.random().nextLong());
        newEntity.setBrandId(firstEntity.getBrandId());
        newEntity.setPriceListId(firstEntity.getPriceListId());
        newEntity.setStartDate(startDate);
        newEntity.setEndDate(endDate);
        newEntity.setPriority(firstEntity.getPriority());
        newEntity.setPartNumber(firstEntity.getPartNumber());
        newEntity.setPrice(firstEntity.getPrice());
        newEntity.setCurrencyISO(firstEntity.getCurrencyISO());
        return newEntity;
    }

    public static OfferEntity after(final OfferEntity firstEntity) {
        final var startDate = firstEntity.getEndDate().plus(ONE, ChronoUnit.DAYS);
        final var endDate = startDate.plus(ONE, ChronoUnit.HOURS);
        final var newEntity = new OfferEntity();
        newEntity.setId(FAKER.random().nextLong());
        newEntity.setBrandId(firstEntity.getBrandId());
        newEntity.setPriceListId(firstEntity.getPriceListId());
        newEntity.setStartDate(startDate);
        newEntity.setEndDate(endDate);
        newEntity.setPriority(firstEntity.getPriority());
        newEntity.setPartNumber(firstEntity.getPartNumber());
        newEntity.setPrice(firstEntity.getPrice());
        newEntity.setCurrencyISO(firstEntity.getCurrencyISO());
        return newEntity;
    }
}
