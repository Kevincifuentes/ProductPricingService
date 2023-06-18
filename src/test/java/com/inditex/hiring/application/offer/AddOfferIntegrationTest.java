package com.inditex.hiring.application.offer;

import com.inditex.hiring.application.cqrs.Command;
import com.inditex.hiring.application.cqrs.CommandBus;
import com.inditex.hiring.application.cqrs.CommandNotFoundException;
import com.inditex.hiring.domain.ports.OfferReader;
import com.inditex.hiring.infrastructure.Application;
import com.inditex.hiring.infrastructure.persistence.jpa.write.OfferJPARepository;
import com.inditex.hiring.objectmother.application.AddOfferCommandMother;
import com.inditex.hiring.objectmother.infrastructure.persistence.jpa.read.model.OfferViewMother;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AddOfferIntegrationTest {

    @Autowired
    private CommandBus testSubject;

    @Autowired
    private OfferReader offerReader;

    @Autowired
    private OfferJPARepository offerJPARepository;

    @After
    public void tearDown() {
        offerJPARepository.deleteAll();
    }

    @Test
    public void shouldThrowNotFoundException() {
        // given
        final var unknownCommand = new UnknownCommand();

        //when
        final var throwable = catchThrowable(() -> testSubject.execute(unknownCommand));

        //then
        assertThat(throwable).isInstanceOf(CommandNotFoundException.class);
    }

    @Test
    public void shouldAddOffer() {
        // given
        final var addOfferCommand = AddOfferCommandMother.random();

        //when
        testSubject.execute(addOfferCommand);

        //then
        final var storedOffers = offerReader.findAll();
        assertThat(storedOffers)
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactly(OfferViewMother.from(addOfferCommand));
    }

    /** Test command
     */
    public static class UnknownCommand implements Command {

    }
}
