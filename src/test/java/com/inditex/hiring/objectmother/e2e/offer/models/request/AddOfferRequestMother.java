package com.inditex.hiring.objectmother.e2e.offer.models.request;

import com.inditex.hiring.e2e.offer.models.request.AddOfferRequest;
import com.inditex.hiring.objectmother.InstantiationNotAllowed;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static com.inditex.hiring.TestSuiteUtils.FAKER;

public final class AddOfferRequestMother {
    private static final int MIN_LONG_VALUE = 1;
    private static final int ONE = 1;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH.mm.ss'Z'")
            .withZone(ZoneId.of("UTC"));

    private AddOfferRequestMother() throws InstantiationNotAllowed {
        throw new InstantiationNotAllowed(this.getClass().getName());
    }

    public static AddOfferRequest random() {
        return new AddOfferRequest(
                FAKER.random().nextLong(MIN_LONG_VALUE, Long.MAX_VALUE),
                FAKER.random().nextInt(),
                FAKER.random().nextLong(MIN_LONG_VALUE, Long.MAX_VALUE),
                DATE_TIME_FORMATTER.format(Instant.now()),
                DATE_TIME_FORMATTER.format(Instant.now().plus(ONE, ChronoUnit.DAYS)),
                FAKER.random().nextInt(),
                FAKER.commerce().brand(),
                BigDecimal.valueOf(FAKER.random().nextDouble()).setScale(2, RoundingMode.CEILING),
                FAKER.currency().code()
        );
    }

    public static AddOfferRequest before(final AddOfferRequest addOfferRequest) {
        final var endDate = parseStringDateToInstant(addOfferRequest.startDate())
                .minus(ONE, ChronoUnit.DAYS);
        final var startDate = endDate.minus(ONE, ChronoUnit.HOURS);
        return buildAddOfferRequest(addOfferRequest, startDate, endDate);
    }

    public static AddOfferRequest after(final AddOfferRequest addOfferRequest) {
        final var startDate = parseStringDateToInstant(addOfferRequest.endDate())
                .plus(ONE, ChronoUnit.DAYS);
        final var endDate = startDate.plus(ONE, ChronoUnit.HOURS);
        return buildAddOfferRequest(addOfferRequest, startDate, endDate);
    }

    private static AddOfferRequest buildAddOfferRequest(AddOfferRequest addOfferRequest, Instant startDate, Instant endDate) {
        return addOfferRequest.toBuilder()
                .offerId(FAKER.random().nextLong(MIN_LONG_VALUE, Long.MAX_VALUE))
                .startDate(DATE_TIME_FORMATTER.format(startDate))
                .endDate(DATE_TIME_FORMATTER.format(endDate))
                .build();
    }

    private static Instant parseStringDateToInstant(final String dateString) {
        return Instant.from(DATE_TIME_FORMATTER.parse(dateString));
    }

    public static AddOfferRequest randomEurWith(final String startDate, final String endDate, final int priority, BigDecimal price) {
        return AddOfferRequest.builder()
                .offerId(FAKER.random().nextLong(MIN_LONG_VALUE, Long.MAX_VALUE))
                .brandId(FAKER.random().nextInt())
                .startDate(startDate)
                .endDate(endDate)
                .currencyIso("EUR")
                .productPartnumber(FAKER.commerce().brand())
                .priority(priority)
                .priceListId(FAKER.random().nextLong(MIN_LONG_VALUE, Long.MAX_VALUE))
                .price(price)
                .build();
    }

    public static AddOfferRequest withEurCurrency(AddOfferRequest exampleAddOfferRequest, String startDate, String endDate, int priority, BigDecimal price) {
        return exampleAddOfferRequest.toBuilder()
                .offerId(FAKER.random().nextLong(MIN_LONG_VALUE, Long.MAX_VALUE))
                .startDate(startDate)
                .endDate(endDate)
                .priority(priority)
                .price(price)
                .currencyIso("EUR")
                .build();
    }
}
