package com.inditex.hiring.infrastructure.persistence.ports;

import com.inditex.hiring.domain.Offer;

public interface OfferWriter {
    void save(final Offer offer);
}
