package com.inditex.hiring.application.offer;

import com.inditex.hiring.application.cqrs.QueryHandler;
import com.inditex.hiring.domain.ports.OfferReader;
import com.inditex.hiring.infrastructure.persistence.jpa.read.model.OfferView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class GetOfferHandler implements QueryHandler<GetOfferQuery, Optional<OfferView>> {

    private final OfferReader offerReader;

    @Override
    public Optional<OfferView> ask(GetOfferQuery query) {
        return offerReader.findById(query.offerId());
    }
}
