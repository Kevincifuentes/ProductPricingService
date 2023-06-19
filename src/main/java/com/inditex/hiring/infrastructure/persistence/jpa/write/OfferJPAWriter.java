package com.inditex.hiring.infrastructure.persistence.jpa.write;

import com.inditex.hiring.domain.Offer;
import com.inditex.hiring.domain.ports.OfferWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class OfferJPAWriter implements OfferWriter {

    private final OfferJPARepository offerJPARepository;

    @Override
    public void save(final Offer offer) {
        final var offerEntity = buildOfferEntity(offer);
        offerJPARepository.save(offerEntity);
    }

    @Override
    public void deleteById(long offerId) {
        offerJPARepository.deleteById(offerId);
    }

    private OfferEntity buildOfferEntity(final Offer offer) {
        final var offerEntity = new OfferEntity();
        offerEntity.setId(offer.getId());
        offerEntity.setBrandId(offer.getBrandId());
        offerEntity.setPriceListId(offer.getPriceListId());
        offerEntity.setStartDate(offer.getStartDate());
        offerEntity.setEndDate(offer.getEndDate());
        offerEntity.setPriority(offer.getPriority());
        offerEntity.setPartNumber(offer.getPartNumber());
        offerEntity.setPrice(offer.getPrice());
        offerEntity.setCurrencyISO(offer.getCurrencyISO());
        return offerEntity;
    }
}
