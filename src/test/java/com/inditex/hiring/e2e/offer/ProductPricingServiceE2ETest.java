package com.inditex.hiring.e2e.offer;

import com.inditex.hiring.e2e.offer.models.request.AddOfferRequest;
import com.inditex.hiring.infrastructure.Application;
import com.inditex.hiring.infrastructure.controller.dto.Offer;
import com.inditex.hiring.infrastructure.controller.dto.OfferByPartNumber;
import com.inditex.hiring.objectmother.e2e.offer.models.request.AddOfferRequestMother;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Stream;

import static com.inditex.hiring.TestSuiteUtils.FAKER;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class ProductPricingServiceE2ETest {

    @LocalServerPort
    private Integer port;

    @After
    public void cleanUp() {
        shouldDeleteAll();
    }

    @Test
    public void shouldCreateANewOffer() {
        //then
        createRandomOffer();
    }

    @Test
    public void shouldFindById() {
        //given
        final var expectedId = createRandomOffer();

        final var offer =
                given().
                    contentType(ContentType.JSON).
                when().
                    port(port).
                    get(format("/offer/%s", expectedId)).
                then().
                    log().ifValidationFails().
                    statusCode(HttpStatus.OK.value())
                    .extract()
                    .as(Offer.class);

        //then
        assertThat(offer.getOfferId()).isEqualTo(expectedId);
    }

    @Test
    public void shouldFindByIdNotFound() {
        //given
        final var expectedId = FAKER.number().randomNumber();

        offerNotFoundBy(expectedId);
    }

    @Test
    public void shouldFindAll() {
        //given
        final var expectedId = createRandomOffer();
        final var expectedSecondId = createRandomOffer();

        final var offers = findAllOffers();

        //then
        assertThat(offers)
                .extracting(Offer::getOfferId)
                .contains(expectedId, expectedSecondId);
    }

    @Test
    public void shouldFindAllAsTimetable() {
        //given
        final var secondOfferOnTimeline = AddOfferRequestMother.random();
        createOffer(secondOfferOnTimeline);
        final var thirdOfferOnTimeLine = AddOfferRequestMother.after(secondOfferOnTimeline);
        final var firstOfferOnTimeline = AddOfferRequestMother.before(secondOfferOnTimeline);
        createOffer(thirdOfferOnTimeLine);
        createOffer(firstOfferOnTimeline);

        final var offersTimetable = List.of(
                given().
                        contentType(ContentType.JSON).
                        when().
                        port(port).
                        get(
                                format(
                                        "/brand/%s/partnumber/%s/offer",
                                        secondOfferOnTimeline.brandId(),
                                        secondOfferOnTimeline.productPartnumber()
                                )).
                        then().
                        log().ifValidationFails().
                        statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(OfferByPartNumber[].class)
        );

        //then
        final var expectedOffers = Stream.of(
                firstOfferOnTimeline.toOfferByPartNumber(),
                secondOfferOnTimeline.toOfferByPartNumber(),
                thirdOfferOnTimeLine.toLastOfferByPartNumber()
        ).toList();
        assertThat(offersTimetable).isEqualTo(expectedOffers);
    }

    @Test
    public void shouldFindAllAsTimetableProvidedExample() {
        //given
        final var firstOfferOnTimeline = AddOfferRequestMother.randomWith(
                "2020-06-14T00.00.00Z",
                "2020-12-31T23.59.59Z",
                0
        );
        createOffer(firstOfferOnTimeline);
        final var secondOfferOnTimeline = AddOfferRequestMother.with(
                firstOfferOnTimeline,
                "2020-06-14T15.00.00Z",
                "2020-06-14T18.30.00Z",
                1
        );
        createOffer(secondOfferOnTimeline);
        final var thirdOfferOnTimeLine = AddOfferRequestMother.with(
                firstOfferOnTimeline,
                "2020-06-15T00.00.00Z",
                "2020-06-15T11.00.00Z",
                1
        );
        createOffer(thirdOfferOnTimeLine);
        final var forthOfferOnTimeLine = AddOfferRequestMother.with(
                firstOfferOnTimeline,
                "2020-06-15T16.00.00Z",
                "2020-12-31T23.59.59Z",
                1
        );
        createOffer(forthOfferOnTimeLine);

        final var offersTimetable = List.of(
                given().
                        contentType(ContentType.JSON).
                        when().
                        port(port).
                        get(
                                format(
                                        "/brand/%s/partnumber/%s/offer",
                                        secondOfferOnTimeline.brandId(),
                                        secondOfferOnTimeline.productPartnumber()
                                )).
                        then().
                        log().ifValidationFails().
                        statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(OfferByPartNumber[].class)
        );

        //then
        assertThat(offersTimetable).hasSize(6);
    }

    @Test
    public void shouldDeleteById() {
        //given
        final var expectedId = createRandomOffer();

        given().
            contentType(ContentType.JSON).
        when().
            port(port).
            delete(format("/offer/%s", expectedId)).
        then().
            log().ifValidationFails().
            statusCode(HttpStatus.OK.value());

        //then
        offerNotFoundBy(expectedId);
    }

    @Test
    public void shouldDeleteAll() {
        //given
        shouldFindAll();

        deleteAll();

        //then
        assertThat(findAllOffers()).isEmpty();
    }

    private long createRandomOffer() {
        final var addOfferRequest = AddOfferRequestMother.random();
        return createOffer(addOfferRequest);
    }

    private long createOffer(final AddOfferRequest addOfferRequest) {
        given().
                contentType(ContentType.JSON).
                body(addOfferRequest).
                when().
                port(port).
                post("/offer").
                then().
                log().ifValidationFails().
                statusCode(HttpStatus.CREATED.value());
        return addOfferRequest.offerId();
    }

    private List<Offer> findAllOffers() {
        return List.of(
                given().
                        contentType(ContentType.JSON).
                        when().
                        port(port).
                        get("/offer").
                        then().
                        log().ifValidationFails().
                        statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(Offer[].class)
        );
    }

    private void offerNotFoundBy(long expectedId) {
        given().
                contentType(ContentType.JSON).
                when().
                port(port).
                get(format("/offer/%s", expectedId)).
                then().
                log().ifValidationFails().
                statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void deleteAll() {
        given().
                contentType(ContentType.JSON).
                when().
                port(port).
                delete("/offer").
                then().
                log().ifValidationFails().
                statusCode(HttpStatus.OK.value());
    }
}
