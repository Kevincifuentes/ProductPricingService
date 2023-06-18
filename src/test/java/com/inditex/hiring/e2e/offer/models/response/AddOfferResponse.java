package com.inditex.hiring.e2e.offer.models.response;

public record AddOfferResponse(long id, int brandId, long priceListId, String startDate,
                               String endDate, int priority, String partNumber, double price, String currencyISO) {
}
