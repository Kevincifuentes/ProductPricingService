package com.inditex.hiring.e2e.offer.models.request;

import com.inditex.hiring.infrastructure.controller.dto.OfferByPartNumber;
import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record AddOfferRequest(long offerId, int brandId, long priceListId, String startDate, String endDate,
                              int priority, String productPartnumber, BigDecimal price, String currencyIso) {

    public OfferByPartNumber toOfferByPartNumber() {
        return new OfferByPartNumber(startDate, endDate, price, currencyIso);
    }
}
