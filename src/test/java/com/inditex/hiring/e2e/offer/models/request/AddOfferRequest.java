package com.inditex.hiring.e2e.offer.models.request;

import java.time.Instant;

public record AddOfferRequest(long brandId, long productId, long priceListId, Instant startDate,
                              Instant endDate, long priority, String partNumber, double price, String currencyISO) {
}
