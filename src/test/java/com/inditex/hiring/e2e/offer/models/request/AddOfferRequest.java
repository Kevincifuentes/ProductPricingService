package com.inditex.hiring.e2e.offer.models.request;

public record AddOfferRequest(int brandId, long priceListId, String startDate,
                              String endDate, long priority, String partNumber, double price, String currencyISO) {
}
