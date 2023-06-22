package com.inditex.hiring.infrastructure.controller.dto;

import com.inditex.hiring.infrastructure.persistence.jpa.read.model.OfferView;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Use this POJO on the reponse for brand & partnumber & offer endPoint.
 */
@Data
public class OfferByPartNumber implements Serializable {
  private static final DateTimeFormatter DATE_EXPECTED_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
          .withZone(ZoneId.of("UTC"));

  private String startDate;

  private String endDate;

  private BigDecimal price;

  private String currencyIso;

  public OfferByPartNumber(String startDate, String endDate, BigDecimal price, String currencyIso) {

    this.startDate = startDate;
    this.endDate = endDate;
    this.price = price;
    this.currencyIso = currencyIso;
  }

  public static OfferByPartNumber from(final OfferView offerView) {
    return new OfferByPartNumber(
            formatDate(offerView.getStartDate()),
            formatDate(offerView.getEndDate()),
            offerView.getPrice(),
            offerView.getCurrencyISO()
    );
  }

  private static String formatDate(final Instant instant) {
    return DATE_EXPECTED_PATTERN.format(instant);
  }

  public static OfferByPartNumber from(Instant startB, Instant endB, BigDecimal price, String currencyISO) {
    return new OfferByPartNumber(
            startB.toString(),
            endB.toString(),
            price,
            currencyISO
    );
  }
}