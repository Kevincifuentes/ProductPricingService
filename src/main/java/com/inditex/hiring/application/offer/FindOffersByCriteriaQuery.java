package com.inditex.hiring.application.offer;

import com.inditex.hiring.application.cqrs.Query;
import com.inditex.hiring.infrastructure.persistence.jpa.read.model.OfferView;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class FindOffersByCriteriaQuery implements Query<List<OfferView>> {
    OfferCriteria offerCriteria;
    String partNumber;
    boolean shouldSortByStartEndDate;
}
