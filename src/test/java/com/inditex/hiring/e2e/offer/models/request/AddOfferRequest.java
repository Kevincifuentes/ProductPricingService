package com.inditex.hiring.e2e.offer.models.request;

import com.inditex.hiring.infrastructure.controller.dto.Offer;
import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record AddOfferRequest(long offerId, int brandId, long priceListId, String startDate, String endDate,
                              int priority, String partNumber, BigDecimal price, String currencyISO) {

    public Offer toOffer() {
        return new Offer(offerId, brandId, startDate, endDate, priceListId, partNumber, priority, price, currencyISO);
    }
}
