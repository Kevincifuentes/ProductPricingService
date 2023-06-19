package com.inditex.hiring.domain.ports;

import com.inditex.hiring.application.offer.OfferCriteria;
import com.inditex.hiring.infrastructure.persistence.jpa.read.model.OfferView;

import java.util.List;
import java.util.Optional;

public interface OfferReader {
    Optional<OfferView> findById(long offerId);
    List<OfferView> findAll();
    List<OfferView> findByCriteria(OfferCriteria criteria);
}
