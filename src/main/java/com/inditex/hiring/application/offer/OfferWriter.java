package com.inditex.hiring.application.offer;

import com.inditex.hiring.domain.Offer;

public interface OfferWriter {
    void save(final Offer offer);
}
