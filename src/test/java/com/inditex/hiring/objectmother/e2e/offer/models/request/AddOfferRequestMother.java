package com.inditex.hiring.objectmother.e2e.offer.models.request;

import com.inditex.hiring.e2e.offer.models.request.AddOfferRequest;
import com.inditex.hiring.objectmother.InstantiationNotAllowed;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static com.inditex.hiring.TestSuiteUtils.FAKER;

public final class AddOfferRequestMother {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH.mm.ss'Z'")
            .withZone(ZoneId.of("UTC"));

    private AddOfferRequestMother() throws InstantiationNotAllowed {
        throw new InstantiationNotAllowed(this.getClass().getName());
    }

    public static AddOfferRequest random() {
        return new AddOfferRequest(
                FAKER.random().nextInt(),
                FAKER.random().nextLong(),
                DATE_TIME_FORMATTER.format(Instant.now()),
                DATE_TIME_FORMATTER.format(Instant.now()),
                FAKER.random().nextInt(),
                FAKER.commerce().brand(),
                FAKER.random().nextDouble(),
                FAKER.currency().code()
        );
    }
}
