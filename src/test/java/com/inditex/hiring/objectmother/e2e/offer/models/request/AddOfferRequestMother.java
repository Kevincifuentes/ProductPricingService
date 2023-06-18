package com.inditex.hiring.objectmother.e2e.offer.models.request;

import com.inditex.hiring.e2e.offer.models.request.AddOfferRequest;
import com.inditex.hiring.objectmother.InstantiationNotAllowed;

import java.time.Instant;

import static com.inditex.hiring.TestSuiteUtils.FAKER;

public final class AddOfferRequestMother {

    private AddOfferRequestMother() throws InstantiationNotAllowed {
        throw new InstantiationNotAllowed(this.getClass().getName());
    }

    public static AddOfferRequest random() {
        return new AddOfferRequest(
                FAKER.random().nextLong(),
                FAKER.random().nextLong(),
                FAKER.random().nextLong(),
                Instant.now(),
                Instant.now(),
                FAKER.random().nextLong(),
                FAKER.commerce().brand(),
                FAKER.random().nextDouble(),
                FAKER.currency().code()
        );
    }
}
