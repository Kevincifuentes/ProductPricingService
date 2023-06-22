package com.inditex.hiring.e2e.offer.models.request;

import com.inditex.hiring.infrastructure.controller.dto.OfferByPartNumber;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Builder(toBuilder = true)
public record AddOfferRequest(long offerId, int brandId, long priceListId, String startDate, String endDate,
                              int priority, String productPartnumber, BigDecimal price, String currencyIso) {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH.mm.ss'Z'")
            .withZone(ZoneId.of("UTC"));
    private static final DateTimeFormatter EXPECTED_OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .withZone(ZoneId.of("UTC"));

    public OfferByPartNumber toOfferByPartNumber() {
        final var endDateInstant = parseStringDateToInstant(this.endDate);
        final var resultEndDate = endDateInstant.minusSeconds(1);
        return new OfferByPartNumber(
                formatToOutputPattern(this.startDate),
                toStringFromInstant(resultEndDate),
                price,
                currencyIso
        );
    }

    public OfferByPartNumber toLastOfferByPartNumber() {
        return new OfferByPartNumber(
                formatToOutputPattern(startDate),
                formatToOutputPattern(endDate),
                price,
                currencyIso
        );
    }

    private static String formatToOutputPattern(final String inputPattern) {
        final var dateInstant = parseStringDateToInstant(inputPattern);
        return EXPECTED_OUTPUT_FORMATTER.format(dateInstant);
    }

    private static String toStringFromInstant(final Instant instant) {
        return EXPECTED_OUTPUT_FORMATTER.format(instant);
    }

    private static Instant parseStringDateToInstant(final String dateString) {
        return Instant.from(DATE_TIME_FORMATTER.parse(dateString));
    }
}
