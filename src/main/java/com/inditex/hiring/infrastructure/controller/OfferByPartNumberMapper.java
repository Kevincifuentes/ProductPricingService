package com.inditex.hiring.infrastructure.controller;

import com.inditex.hiring.infrastructure.controller.dto.Interval;
import com.inditex.hiring.infrastructure.controller.dto.OfferByPartNumber;
import com.inditex.hiring.infrastructure.persistence.jpa.read.model.OfferView;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

public class OfferByPartNumberMapper {

    private OfferByPartNumberMapper() {
        throw new UnsupportedOperationException("This class should not be instantiated");
    }

    public static List<OfferByPartNumber> buildOfferByPartNumbers(final List<OfferView> offerViews) {
        final var allInstants = getAllInstantsSorted(offerViews);
        return getOfferByPartNumbersByIntervals(allInstants, offerViews);
    }

    private static List<OfferByPartNumber> getOfferByPartNumbersByIntervals(List<Instant> allDates, List<OfferView> offerViews) {
        final var allIntervalsByOfferViews = getIntervalsByAffectedOfferViews(allDates, offerViews);
        return allIntervalsByOfferViews.stream()
                .map(intervalToOfferViewsEntry -> findOfferByPartNumber(intervalToOfferViewsEntry, allIntervalsByOfferViews))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted(Comparator.comparing(OfferByPartNumber::getStartDate))
                .toList();
    }

    private static List<Map.Entry<Interval, List<OfferView>>> getIntervalsByAffectedOfferViews(List<Instant> allDates, List<OfferView> offerViews) {
        return IntStream.range(0, allDates.size() - 1)
                .mapToObj(index -> buildInterval(allDates.get(index), allDates.get(index + 1)))
                .map(interval -> findIntervalToAffectedOfferViewMap(interval, offerViews))
                .flatMap(map -> map.entrySet().stream())
                .toList();
    }

    private static Optional<OfferByPartNumber> findOfferByPartNumber(
            Map.Entry<Interval, List<OfferView>> entry, List<Map.Entry<Interval, List<OfferView>>> allIntervalsByOfferViews
    ) {
        final var interval = entry.getKey();
        final var endDate = buildEndDate(interval, allIntervalsByOfferViews);
        return entry.getValue().stream()
                .max(Comparator.comparing(OfferView::getPriority))
                .map(offerView -> OfferByPartNumber.from(interval.startDate(), endDate, offerView.getPrice(), offerView.getCurrencyISO()));
    }

    private static Instant buildEndDate(final Interval interval, final List<Map.Entry<Interval, List<OfferView>>> allIntervalsByOfferViews) {
        final var lastIntervalByOfferView = allIntervalsByOfferViews.get(allIntervalsByOfferViews.size() - 1);
        final var isLastInterval = lastIntervalByOfferView.getKey() == interval;
        final var endDate = interval.endDate();
        if(isLastInterval) {
            return endDate;
        }

        return endDate.minus(1, ChronoUnit.SECONDS);
    }

    private static Map<Interval, List<OfferView>> findIntervalToAffectedOfferViewMap(
            final Interval interval, final List<OfferView> offerViews
    ) {
        final var matchingOfferViewsByInterval = offerViews.stream()
                .filter(offerView -> coversInterval(offerView, interval.startDate(), interval.endDate()))
                .toList();
        return Map.of(interval, matchingOfferViewsByInterval);
    }

    private static Interval buildInterval(final Instant startDate, final Instant endDate) {
        return new Interval(startDate, endDate);
    }

    private static boolean coversInterval(final OfferView offerView, final Instant initialDate, final Instant endDate) {
        final var offerViewStart = offerView.getStartDate();
        final var offerViewEnd = offerView.getEndDate();
        return isInstantWithinRange(initialDate, endDate, offerViewStart, offerViewEnd);
    }

    public static boolean isInstantWithinRange(
            final Instant start, final Instant end, final Instant startRange, final Instant endRange
    ) {
        return (start.isAfter(startRange) || start.equals(startRange))
                && (start.isBefore(endRange) || start.equals(endRange))
                && (end.isAfter(startRange) || end.equals(startRange))
                && (end.isBefore(endRange) || end.equals(endRange));
    }

    private static List<Instant> getAllInstantsSorted(final List<OfferView> offerViews) {
        return offerViews.stream()
                .map(OfferView::getBothDates)
                .flatMap(List::stream)
                .distinct()
                .sorted()
                .toList();
    }
}
