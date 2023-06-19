package com.inditex.hiring.application.offer;

import com.inditex.hiring.application.cqrs.QueryHandler;
import com.inditex.hiring.domain.ports.OfferReader;
import com.inditex.hiring.infrastructure.persistence.jpa.read.model.OfferView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class FindOffersByCriteriaHandler implements QueryHandler<FindOffersByCriteriaQuery, List<OfferView>> {

    private final OfferReader offerReader;

    @Override
    public List<OfferView> ask(final FindOffersByCriteriaQuery query) {
        return offerReader.findByCriteria(query.getOfferCriteria());
    }
}
