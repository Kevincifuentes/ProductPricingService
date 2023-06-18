package com.inditex.hiring.infrastructure.persistence;

import com.inditex.hiring.domain.Offer;

public interface OfferWriter {
    void save(final Offer offer);
}
