package com.inditex.hiring.objectmother.infrastructure.persistence.jpa.read.model;

import com.inditex.hiring.domain.Offer;
import com.inditex.hiring.infrastructure.persistence.jpa.write.OfferEntity;
import com.inditex.hiring.objectmother.InstantiationNotAllowed;
import com.inditex.hiring.objectmother.domain.OfferMother;

public class OfferEntityMother {

    private OfferEntityMother() throws InstantiationNotAllowed {
        throw new InstantiationNotAllowed(this.getClass().getName());
    }

    public static OfferEntity random() {
        return from(OfferMother.random());
    }

    public static OfferEntity from(final Offer offer) {
        final var entity = new OfferEntity();
        entity.setBrandId(offer.getBrandId());
        entity.setProductId(offer.getProductId());
        entity.setPriceListId(offer.getPriceListId());
        entity.setStartDate(offer.getStartDate());
        entity.setEndDate(offer.getEndDate());
        entity.setPriority(offer.getPriority());
        entity.setPartNumber(offer.getPartNumber());
        entity.setPrice(offer.getPrice());
        entity.setCurrencyISO(offer.getCurrencyISO());
        return entity;
    }
}
