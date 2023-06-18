package com.inditex.hiring.infrastructure.persistence.jpa.write;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Entity
@Table(name="OFFER")
public class OfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "BRAND_ID")
    private Long brandId;
    @Column(name = "PRICE_LIST")
    private Long priceListId;
    @Column(name = "START_DATE")
    private Instant startDate;
    @Column(name = "END_DATE")
    private Instant endDate;
    @Column(name = "PRIORITY")
    private Long priority;
    @Column(name = "PARTNUMBER")
    private String partNumber;
    @Column(name = "PRICE")
    private BigDecimal price;
    @Column(name = "CURR")
    private String currencyISO;
}
