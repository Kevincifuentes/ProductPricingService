package com.inditex.hiring.application.offer;

import com.inditex.hiring.application.cqrs.Query;
import com.inditex.hiring.infrastructure.persistence.jpa.read.model.OfferView;

import java.util.List;

public record FindAllOffersQuery() implements Query<List<OfferView>> {
}
