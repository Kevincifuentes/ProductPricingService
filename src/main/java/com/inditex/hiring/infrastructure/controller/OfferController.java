package com.inditex.hiring.infrastructure.controller;

import com.inditex.hiring.application.cqrs.CommandBus;
import com.inditex.hiring.application.offer.AddOfferCommand;
import com.inditex.hiring.application.offer.GetOfferHandler;
import com.inditex.hiring.application.offer.GetOfferQuery;
import com.inditex.hiring.infrastructure.controller.dto.Offer;
import com.inditex.hiring.infrastructure.controller.dto.OfferByPartNumber;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * You can change this controller but please do not change ends points signatures & payloads.
 */
@AllArgsConstructor
@RestController
public class OfferController {

  private final CommandBus commandBus;
  private final GetOfferHandler getOfferHandler;

  //TODO: Refactor endpoints to use /offers plural instead, using singular doesn't comply with REST standards.
  @PostMapping(value = "/offer", consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public void createNewOffer(@RequestBody @Valid Offer offer) {
    commandBus.execute(buildAddOfferCommand(offer));
  }

  @DeleteMapping(value = "/offer")
  @ResponseStatus(HttpStatus.OK)
  public void deleteAllOffers() {

    //TODO implement it!.

  }

  @DeleteMapping(value = "/offer/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteOfferById(@RequestParam Long id) {

    //TODO implement it!.

  }

  @GetMapping(value = "/offer")
  @ResponseStatus(HttpStatus.OK)
  public List<Offer> getAllOffers() {

    //TODO implement it!.
    return new ArrayList<>();

  }

  @GetMapping(value = "/offer/{offerId}")
  @ResponseStatus(HttpStatus.OK)
  public Offer getOfferById(@PathVariable final Long offerId) {
    return Offer.from(getOfferHandler.ask(new GetOfferQuery(offerId)));
  }

  @GetMapping(value = "brand/{brandId}/partnumber/{partnumber}/offer")
  @ResponseStatus(HttpStatus.OK)
  public List<OfferByPartNumber> getOfferByPartNumber(Integer brandId, String partnumber) {

    //TODO implement it!.
    return new ArrayList<>();
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
