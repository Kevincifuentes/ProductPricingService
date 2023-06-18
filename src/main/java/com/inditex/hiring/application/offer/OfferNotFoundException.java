package com.inditex.hiring.application.offer;

import static java.lang.String.format;

public class OfferNotFoundException extends RuntimeException {

    private static final String NOT_FOUND_OFFER_TEMPLATE = "No Offer found for id %s";

    public OfferNotFoundException(final long offerId) {
        super(format(NOT_FOUND_OFFER_TEMPLATE, offerId));
    }
}
