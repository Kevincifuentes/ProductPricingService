package com.inditex.hiring.domain.ports;

import com.inditex.hiring.domain.Offer;

public interface OfferWriter {
    void save(final Offer offer);

    void deleteById(final long offerId);
}
