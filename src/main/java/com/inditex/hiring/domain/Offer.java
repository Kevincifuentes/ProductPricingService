package com.inditex.hiring.domain;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class Offer {
    @Nullable
    long id;
    long brandId;
    long productId;
    long priceListId;
    Instant startDate;
    Instant endDate;
    long priority;
    String partNumber;
    double price;
    String currencyISO;
}
