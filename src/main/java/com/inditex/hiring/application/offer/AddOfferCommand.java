package com.inditex.hiring.application.offer;

import com.inditex.hiring.application.cqrs.Command;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
public class AddOfferCommand implements Command {
    int brandId;
    long priceListId;
    Instant startDate;
    Instant endDate;
    int priority;
    String partNumber;
    BigDecimal price;
    String currencyISO;
}
