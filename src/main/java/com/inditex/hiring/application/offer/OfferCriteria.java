package com.inditex.hiring.application.offer;

import lombok.Builder;
import lombok.Value;

import java.util.Optional;

import static java.util.Optional.of;

@Value
@Builder
public class OfferCriteria {
    int brandId;
    String partNumber;
    Boolean shouldSortByStartEndDate;

    public Optional<Integer> findBrandId() {
        return of(brandId);
    }

    public Optional<String> findPartNumber() {
        return of(partNumber);
    }
}
