package com.inditex.hiring.application.offer;

import com.inditex.hiring.application.cqrs.CommandHandler;
import com.inditex.hiring.domain.ports.OfferWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DeleteAllOffersHandler implements CommandHandler<DeleteAllOffersCommand> {

    private final OfferWriter offerWriter;

    @Override
    public void execute(DeleteAllOffersCommand command) {
        offerWriter.deleteAll();
    }
}
