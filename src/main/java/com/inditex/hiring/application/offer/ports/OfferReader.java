package com.inditex.hiring.application.offer.ports;

import com.inditex.hiring.domain.Offer;

import java.util.List;

public interface OfferReader {
    List<Offer> findAll();
}
