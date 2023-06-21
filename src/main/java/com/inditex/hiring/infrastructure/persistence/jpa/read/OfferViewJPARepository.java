package com.inditex.hiring.infrastructure.persistence.jpa.read;

import com.inditex.hiring.infrastructure.persistence.jpa.read.model.OfferView;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OfferViewJPARepository extends JpaRepository<OfferView, Long>, JpaSpecificationExecutor<OfferView> {

    static Specification<OfferView> hasBrandId(final long brandId) {
        return (offerView, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(offerView.get("brandId"), brandId);
    }

    static Specification<OfferView> hasPartNumber(final String partNumber) {
        return (offerView, criteriaQuery, cb) -> cb.equal(offerView.get("partNumber"), partNumber);
    }

    static Sort orderByDates() {
        return Sort.by("startDate").and(Sort.by("endDate"));
    }

}
