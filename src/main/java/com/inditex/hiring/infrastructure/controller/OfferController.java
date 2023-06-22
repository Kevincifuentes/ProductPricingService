package com.inditex.hiring.infrastructure.controller;

import com.inditex.hiring.application.cqrs.CommandBus;
import com.inditex.hiring.application.cqrs.QueryBus;
import com.inditex.hiring.application.offer.*;
import com.inditex.hiring.infrastructure.controller.dto.Offer;
import com.inditex.hiring.infrastructure.controller.dto.OfferByPartNumber;
import com.inditex.hiring.infrastructure.persistence.jpa.read.model.OfferView;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

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


  private List<OfferByPartNumber> buildOfferByPartNumbers(final List<OfferView> offerViews) {
    final var allDates = getAllDatesSorted(offerViews);
    return getOfferByPartNumbersFromIntervals(allDates, offerViews);
  }

  private List<OfferByPartNumber> getOfferByPartNumbersFromIntervals(List<Instant> allDates, List<OfferView> offerViews) {
    final var allIntervalsByOfferViews = IntStream.range(0, allDates.size() - 1)
            .mapToObj(index -> buildInterval(allDates.get(index), allDates.get(index + 1)))
            .map(interval -> buildMap(interval, offerViews))
            .flatMap(map -> map.entrySet().stream())
            .toList();
    return allIntervalsByOfferViews.stream()
            .map(intervalListEntry -> buildOfferByPartNumber(intervalListEntry, allIntervalsByOfferViews))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .sorted(Comparator.comparing(OfferByPartNumber::getStartDate))
            .toList();
  }

  private Optional<OfferByPartNumber> buildOfferByPartNumber(
          Map.Entry<Interval, List<OfferView>> entry, List<Map.Entry<Interval, List<OfferView>>> allIntervalsByOfferViews
  ) {
    final var interval = entry.getKey();
    final var endDate = buildEndDate(interval, allIntervalsByOfferViews);
    return entry.getValue().stream()
            .max(Comparator.comparing(OfferView::getPriority))
            .map(offerView -> OfferByPartNumber.from(interval.startDate, endDate, offerView.getPrice(), offerView.getCurrencyISO()));
  }

  private Instant buildEndDate(Interval interval, List<Map.Entry<Interval, List<OfferView>>> allIntervalsByOfferViews) {
    final var lastIntervalByOfferView = allIntervalsByOfferViews.get(allIntervalsByOfferViews.size() - 1);
    final var isLastInterval = lastIntervalByOfferView.getKey() == interval;
    if(isLastInterval) {
      return interval.endDate;
    }

    return interval.endDate.minus(1, ChronoUnit.SECONDS);
  }

  private Map<Interval, List<OfferView>> buildMap(Interval interval, List<OfferView> offerViews) {
    final var matchingOfferViewsByInterval = offerViews.stream()
            .filter(offerView -> coversInterval(offerView, interval.startDate, interval.endDate))
            .toList();
    return Map.of(interval, matchingOfferViewsByInterval);
  }

  private Interval buildInterval(Instant startDate, Instant endDate) {
    return new Interval(startDate, endDate);
  }

  private boolean coversInterval(OfferView offerView, Instant initialDate, Instant endDate) {
    final var offerViewStart = offerView.getStartDate();
    final var offerViewEnd = offerView.getEndDate();
    return isInstantWithinRange(initialDate, endDate, offerViewStart, offerViewEnd);
  }

  public static boolean isInstantWithinRange(Instant instant1, Instant instant2, Instant startRange, Instant endRange) {
    return (instant1.isAfter(startRange) || instant1.equals(startRange))
            && (instant1.isBefore(endRange) || instant1.equals(endRange))
            && (instant2.isAfter(startRange) || instant2.equals(startRange))
            && (instant2.isBefore(endRange) || instant2.equals(endRange));
  }

  public record Interval(Instant startDate, Instant endDate) { }


  private static List<Instant> getAllDatesSorted(final List<OfferView> offerViews) {
    return offerViews.stream()
            .map(OfferController::getInstants)
            .flatMap(List::stream)
            .distinct()
            .sorted()
            .toList();
  }

  private static List<Instant> getInstants(final OfferView offerView) {
    return List.of(offerView.getStartDate(), offerView.getEndDate());
  }
}
