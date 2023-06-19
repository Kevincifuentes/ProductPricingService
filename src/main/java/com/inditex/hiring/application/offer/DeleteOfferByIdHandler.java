package com.inditex.hiring.application.offer;

import com.inditex.hiring.application.cqrs.CommandHandler;
import com.inditex.hiring.domain.ports.OfferWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DeleteOfferByIdHandler implements CommandHandler<DeleteOfferByIdCommand> {

    private final OfferWriter offerWriter;

    @Override
    public void execute(final DeleteOfferByIdCommand command) {
        offerWriter.deleteById(command.id());
    }
}
