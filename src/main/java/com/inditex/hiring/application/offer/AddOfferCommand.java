package com.inditex.hiring.application.offer;

import com.inditex.hiring.application.Command;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class AddOfferCommand implements Command {
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
