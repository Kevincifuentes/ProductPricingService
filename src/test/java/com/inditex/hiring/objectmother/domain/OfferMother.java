package com.inditex.hiring.objectmother.domain;

import com.inditex.hiring.application.offer.AddOfferCommand;
import com.inditex.hiring.domain.Offer;
import com.inditex.hiring.objectmother.InstantiationNotAllowed;

public final class OfferMother {

    private OfferMother() throws InstantiationNotAllowed {
        throw new InstantiationNotAllowed(this.getClass().getName());
    }

    public static Offer from(final AddOfferCommand addOfferCommand) {
        return Offer.builder()
                .brandId(addOfferCommand.getBrandId())
                .productId(addOfferCommand.getProductId())
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
