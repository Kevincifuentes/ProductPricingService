package com.inditex.hiring.e2e.offer;

import com.inditex.hiring.infrastructure.Application;
import com.inditex.hiring.infrastructure.controller.dto.Offer;
import com.inditex.hiring.objectmother.e2e.offer.models.request.AddOfferRequestMother;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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

    @Test
    public void shouldCreateANewOffer() {
        //then
        createOffer();
    }

    @Test
    public void shouldFindById() {
        //given
        final var expectedId = createOffer();

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
    public void shouldFindAll() {
        //given
        final var expectedId = createOffer();
        final var expectedSecondId = createOffer();

        final var offers = List.of(
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

        //then
        assertThat(offers)
                .extracting(Offer::getOfferId)
                .contains(expectedId, expectedSecondId);
    }

    private long createOffer() {
        final var addOfferRequest = AddOfferRequestMother.random();
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
}
