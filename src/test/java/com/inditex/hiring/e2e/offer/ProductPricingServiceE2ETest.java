package com.inditex.hiring.e2e.offer;

import com.inditex.hiring.e2e.offer.models.request.AddOfferRequest;
import com.inditex.hiring.infrastructure.Application;
import com.inditex.hiring.objectmother.e2e.offer.models.request.AddOfferRequestMother;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

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
        //given
        AddOfferRequest addOfferRequest = AddOfferRequestMother.random();

        given().
                contentType(ContentType.JSON).
                body(addOfferRequest).
        when().
                port(port).
                post("/offer").
        then().
                log().ifValidationFails().
                statusCode(HttpStatus.CREATED.value());
    }
}
