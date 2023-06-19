package com.inditex.hiring.application.offer;

import com.inditex.hiring.application.cqrs.Query;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FindOffersByCriteriaQuery implements Query {
    OfferCriteria offerCriteria;
    String partNumber;
    boolean shouldSortByStartEndDate;
}
