package com.inditex.hiring.domain.ports;

import com.inditex.hiring.infrastructure.persistence.jpa.read.model.OfferView;

import java.util.List;

public interface OfferReader {
    List<OfferView> findAll();
}
