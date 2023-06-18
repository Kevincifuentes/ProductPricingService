package com.inditex.hiring.e2e.offer.models.request;

public record AddOfferRequest(long offerId, int brandId, long priceListId, String startDate,
                              String endDate, int priority, String partNumber, double price, String currencyISO) {
}
