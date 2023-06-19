package com.inditex.hiring.infrastructure.persistence.jpa.read;

import com.inditex.hiring.application.offer.OfferCriteria;
import com.inditex.hiring.domain.ports.OfferReader;
import com.inditex.hiring.infrastructure.persistence.jpa.read.model.OfferView;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class OfferJPAReader implements OfferReader {

    private final OfferViewJPARepository offerViewJPARepository;

    @Override
    public Optional<OfferView> findById(final long offerId) {
        return offerViewJPARepository.findById(offerId);
    }

    @Override
    public List<OfferView> findAll() {
        return offerViewJPARepository.findAll();
    }

    @Override
    public List<OfferView> findByCriteria(final OfferCriteria criteria) {
        Specification<OfferView> specification = Specification.where(null);
        criteria.findBrandId()
                .ifPresent(brandId -> specification.and(OfferViewJPARepository.hasBrandId(brandId)));
        criteria.findPartNumber()
                .ifPresent(partNumber -> specification.and(OfferViewJPARepository.hasPartNumber(partNumber)));
        return offerViewJPARepository.findAll(specification, OfferViewJPARepository.orderByDates());
    }
}
