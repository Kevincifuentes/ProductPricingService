package com.inditex.hiring.domain;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Data
public class Offer {
    @Nullable
    long id;
    int brandId;
    long priceListId;
    Instant startDate;
    Instant endDate;
    int priority;
    String partNumber;
    BigDecimal price;
    String currencyISO;
}
