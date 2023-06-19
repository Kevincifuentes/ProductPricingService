package com.inditex.hiring.application.offer;

import com.inditex.hiring.application.cqrs.Query;
import com.inditex.hiring.infrastructure.persistence.jpa.read.model.OfferView;

public record GetOfferQuery (long offerId) implements Query<OfferView> {
}
