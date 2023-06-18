package com.inditex.hiring.application.offer;

import com.inditex.hiring.application.Command;
import com.inditex.hiring.application.CommandBus;
import com.inditex.hiring.application.CommandNotFoundException;
import com.inditex.hiring.objectmother.application.AddOfferCommandMother;
import com.inditex.hiring.objectmother.domain.OfferMother;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
public class AddOfferIntegrationTest {

    @Autowired
    private CommandBus testSubject;

    @Autowired
    private OfferReader offerReader;

    @Test
    public void should_throw_not_found_exception() {
        // given
        final var unknownCommand = new UnknownCommand();

        //when
        final var throwable = catchThrowable(() -> testSubject.execute(unknownCommand));

        //then
        assertThat(throwable).isInstanceOf(CommandNotFoundException.class);
    }

    @Test
    public void should_add_offer() {
        // given
        final var addOfferCommand = AddOfferCommandMother.random();

        //when
        testSubject.execute(addOfferCommand);

        //then
        final var storedOffers = offerReader.findAll();
        assertThat(storedOffers)
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactly(OfferMother.from(addOfferCommand));
    }

    /** Test command
     */
    public static class UnknownCommand implements Command {

    }
}
