package com.inditex.hiring.objectmother.e2e.offer.models.response;

import com.inditex.hiring.e2e.offer.models.request.AddOfferRequest;
import com.inditex.hiring.e2e.offer.models.response.AddOfferResponse;
import com.inditex.hiring.objectmother.InstantiationNotAllowed;

import static com.inditex.hiring.TestSuiteUtils.FAKER;

public final class AddOfferResponseMother {
    private AddOfferResponseMother() throws InstantiationNotAllowed {
        throw new InstantiationNotAllowed(this.getClass().getName());
    }

    public static AddOfferResponse from(final AddOfferRequest addOfferRequest) {
        return new AddOfferResponse(
                FAKER.random().nextLong(),
                addOfferRequest.brandId(),
                addOfferRequest.priceListId(),
                addOfferRequest.startDate().toString(),
                addOfferRequest.endDate().toString(),
                addOfferRequest.priority(),
                addOfferRequest.partNumber(),
                addOfferRequest.price(),
                addOfferRequest.currencyISO()
        );
    }
}
