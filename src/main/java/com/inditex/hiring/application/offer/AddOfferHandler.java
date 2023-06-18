package com.inditex.hiring.application.offer;

import com.inditex.hiring.application.cqrs.CommandHandler;
import com.inditex.hiring.domain.Offer;
import com.inditex.hiring.domain.ports.OfferWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AddOfferHandler implements CommandHandler<AddOfferCommand> {

    private final OfferWriter offerWriter;

    @Override
    public void execute(final AddOfferCommand command) {
        final var offer = buildOffer(command);
        offerWriter.save(offer);
    }

    private Offer buildOffer(final AddOfferCommand command) {
        return Offer.builder()
                .productId(command.getProductId())
                .brandId(command.getBrandId())
                .priceListId(command.getPriceListId())
                .startDate(command.getStartDate())
                .endDate(command.getEndDate())
                .priority(command.getPriority())
                .partNumber(command.getPartNumber())
                .price(command.getPrice())
                .currencyISO(command.getCurrencyISO())
                .build();
    }
}
