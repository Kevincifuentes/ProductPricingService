package com.inditex.hiring.e2e.offer.models.response;

public record AddOfferResponse(long id, long brandId, long priceListId, String startDate,
                               String endDate, long priority, String partNumber, double price, String currencyISO) {
}
