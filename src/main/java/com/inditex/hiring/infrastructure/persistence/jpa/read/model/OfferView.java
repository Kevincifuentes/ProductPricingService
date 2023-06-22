package com.inditex.hiring.infrastructure.persistence.jpa.read.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@Entity
@Table(name="OFFER")
public class OfferView {
    @Id
    private Long id;
    @Column(name = "BRAND_ID")
    private Integer brandId;
    @Column(name = "PRICE_LIST")
    private Long priceListId;
    @Column(name = "START_DATE")
    private Instant startDate;
    @Column(name = "END_DATE")
    private Instant endDate;
    @Column(name = "PRIORITY")
    private Integer priority;
    @Column(name = "PARTNUMBER")
    private String partNumber;
    @Column(name = "PRICE")
    private BigDecimal price;
    @Column(name = "CURR")
    private String currencyISO;

    public List<Instant> getBothDates() {
        return List.of(this.startDate, this.endDate);
    }
}
