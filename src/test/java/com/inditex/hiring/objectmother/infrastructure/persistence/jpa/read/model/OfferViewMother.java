package com.inditex.hiring.objectmother.infrastructure.persistence.jpa.read.model;

import com.inditex.hiring.application.offer.AddOfferCommand;
import com.inditex.hiring.domain.Offer;
import com.inditex.hiring.infrastructure.persistence.jpa.read.model.OfferView;
import com.inditex.hiring.infrastructure.persistence.jpa.write.OfferEntity;
import com.inditex.hiring.objectmother.InstantiationNotAllowed;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

import static com.inditex.hiring.TestSuiteUtils.FAKER;

public final class OfferViewMother {

    public static final int MAX_NUMBER_OF_DECIMALS_PRICE = 2;
    public static final int MIN_VALUE_PRICE = 0;
    public static final int MAX_VALUE_PRICE = 100;
    public static final int MINIMUM_SCALE = 2;

    private OfferViewMother() throws InstantiationNotAllowed {
        throw new InstantiationNotAllowed(this.getClass().getName());
    }

    public static OfferView from(final AddOfferCommand addOfferCommand) {
        final var view = new OfferView();
        view.setBrandId(addOfferCommand.getBrandId());
        view.setPriceListId(addOfferCommand.getPriceListId());
        view.setStartDate(addOfferCommand.getStartDate());
        view.setEndDate(addOfferCommand.getEndDate());
        view.setPriority(addOfferCommand.getPriority());
        view.setPartNumber(addOfferCommand.getPartNumber());
        view.setPrice(getPriceOnMinimumScale(addOfferCommand.getPrice()));
        view.setCurrencyISO(addOfferCommand.getCurrencyISO());
        return view;
    }

    public static OfferView from(final Offer offer) {
        final var view = new OfferView();
        view.setId(offer.getId());
        view.setBrandId(offer.getBrandId());
        view.setPriceListId(offer.getPriceListId());
        view.setStartDate(offer.getStartDate());
        view.setEndDate(offer.getEndDate());
        view.setPriority(offer.getPriority());
        view.setPartNumber(offer.getPartNumber());
        view.setPrice(getPriceOnMinimumScale(offer));
        view.setCurrencyISO(offer.getCurrencyISO());
        return view;
    }

    private static BigDecimal getPriceOnMinimumScale(final Offer offer) {
        return getPriceOnMinimumScale(offer.getPrice());
    }

    private static BigDecimal getPriceOnMinimumScale(final BigDecimal price) {
        return price.setScale(Math.max(MINIMUM_SCALE, price.scale()), RoundingMode.UNNECESSARY);
    }


    public static OfferView from(final OfferEntity offerEntity) {
        final var view = new OfferView();
        view.setId(offerEntity.getId());
        view.setBrandId(offerEntity.getBrandId());
        view.setPriceListId(offerEntity.getPriceListId());
        view.setStartDate(offerEntity.getStartDate());
        view.setEndDate(offerEntity.getEndDate());
        view.setPriority(offerEntity.getPriority());
        view.setPartNumber(offerEntity.getPartNumber());
        view.setPrice(getPriceOnMinimumScale(offerEntity.getPrice()));
        view.setCurrencyISO(offerEntity.getCurrencyISO());
        return view;
    }

    public static OfferView random() {
        final var view = new OfferView();
        view.setId(FAKER.number().randomNumber());
        view.setBrandId(FAKER.random().nextInt());
        view.setPriceListId(FAKER.number().randomNumber());
        view.setStartDate(Instant.now());
        view.setEndDate(Instant.now());
        view.setPriority(FAKER.random().nextInt());
        view.setPartNumber(String.valueOf(FAKER.number().randomNumber()));
        view.setPrice(BigDecimal.valueOf(
                FAKER.number().randomDouble(MAX_NUMBER_OF_DECIMALS_PRICE, MIN_VALUE_PRICE, MAX_VALUE_PRICE))
        );
        view.setCurrencyISO(FAKER.currency().code());
        return view;
    }
}
