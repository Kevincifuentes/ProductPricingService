package com.inditex.hiring.infrastructure.persistence.ports;

import com.inditex.hiring.domain.Offer;

import java.util.List;

public interface OfferReader {
    List<Offer> findAll();
}
