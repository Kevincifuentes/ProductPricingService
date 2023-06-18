package com.inditex.hiring.application.offer;

import com.inditex.hiring.application.cqrs.QueryHandler;
import com.inditex.hiring.domain.ports.OfferReader;
import com.inditex.hiring.infrastructure.persistence.jpa.read.model.OfferView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class GetOfferHandler implements QueryHandler<GetOfferQuery, OfferView> {

    private final OfferReader offerReader;

    @Override
    public OfferView ask(GetOfferQuery query) {
        return offerReader.findById(query.offerId())
                .orElseThrow(() -> new OfferNotFoundException(query.offerId()));
    }
}
