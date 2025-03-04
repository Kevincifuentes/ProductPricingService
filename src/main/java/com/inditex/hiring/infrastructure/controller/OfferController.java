package com.inditex.hiring.infrastructure.controller;

import com.inditex.hiring.application.cqrs.CommandBus;
import com.inditex.hiring.application.cqrs.QueryBus;
import com.inditex.hiring.application.offer.*;
import com.inditex.hiring.infrastructure.controller.dto.Offer;
import com.inditex.hiring.infrastructure.controller.dto.OfferByPartNumber;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.inditex.hiring.infrastructure.controller.OfferByPartNumberMapper.buildOfferByPartNumbers;

/**
 * You can change this controller but please do not change ends points signatures & payloads.
 */
@AllArgsConstructor
@RestController
public class OfferController {

  private final CommandBus commandBus;
  private final QueryBus queryBus;

  //TODO: Refactor endpoints to use /offers plural instead, using singular doesn't comply with REST standards.
  @PostMapping(value = "/offer", consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public void createNewOffer(@RequestBody @Valid final Offer offer) {
    commandBus.execute(buildAddOfferCommand(offer));
  }

  @DeleteMapping(value = "/offer")
  @ResponseStatus(HttpStatus.OK)
  public void deleteAllOffers() {
    commandBus.execute(new DeleteAllOffersCommand());
  }

  @DeleteMapping(value = "/offer/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteOfferById(@PathVariable final Long id) {
    commandBus.execute(new DeleteOfferByIdCommand(id));
  }

  @GetMapping(value = "/offer")
  @ResponseStatus(HttpStatus.OK)
  public List<Offer> getAllOffers() {
    return queryBus.ask(new FindAllOffersQuery())
            .stream()
            .map(Offer::from)
            .toList();
  }

  @GetMapping(value = "/offer/{offerId}")
  @ResponseStatus(HttpStatus.OK)
  public Offer getOfferById(@PathVariable final Long offerId) {
    return Offer.from(queryBus.ask(new GetOfferQuery(offerId)));
  }

  @GetMapping(value = "brand/{brandId}/partnumber/{partnumber}/offer")
  @ResponseStatus(HttpStatus.OK)
  public List<OfferByPartNumber> getOfferByPartNumber(
          @PathVariable final Integer brandId, @PathVariable final String partnumber
  ) {
    final var offerViews = queryBus.ask(buildFindOfferByCriteriaQuery(brandId, partnumber));
    return buildOfferByPartNumbers(offerViews);
  }

  private static FindOffersByCriteriaQuery buildFindOfferByCriteriaQuery(Integer brandId, String partnumber) {
    final var offerCriteria = OfferCriteria.builder()
            .brandId(brandId)
            .partNumber(partnumber)
            .build();
    return FindOffersByCriteriaQuery.builder()
            .offerCriteria(offerCriteria)
            .shouldSortByStartEndDate(true)
            .build();
  }

  //...
  @ExceptionHandler(OfferNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void handleException() {
    //
  }

  private AddOfferCommand buildAddOfferCommand(final Offer offer) {
    return AddOfferCommand.builder()
            .id(offer.getOfferId())
            .brandId(offer.getBrandId())
            .priceListId(offer.getPriceListId())
            .partNumber(offer.getProductPartnumber())
            .price(offer.getPrice())
            .priority(offer.getPriority())
            .currencyISO(offer.getCurrencyIso())
            .startDate(offer.getStartDateAsInstant())
            .endDate(offer.getEndDateAsInstant())
            .build();
  }
}
