package com.inditex.hiring.application.offer;

import com.inditex.hiring.application.cqrs.Query;

public record GetOfferQuery (long offerId) implements Query {
}
