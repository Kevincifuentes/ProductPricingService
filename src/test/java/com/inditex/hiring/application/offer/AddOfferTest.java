package com.inditex.hiring.application.offer;

import com.inditex.hiring.application.offer.ports.OfferWriter;
import com.inditex.hiring.objectmother.application.AddOfferCommandMother;
import com.inditex.hiring.objectmother.domain.OfferMother;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class AddOfferTest {

    @Mock
    private OfferWriter offerWriter;

    @InjectMocks
    private AddOfferHandler testSubject;

    @Test
    public void should_add_offer() {
        // given
        final var addOfferCommand = AddOfferCommandMother.random();

        //when
        testSubject.execute(addOfferCommand);

        //then
        final var expectedOffer = OfferMother.from(addOfferCommand);
        verify(offerWriter).save(expectedOffer);
    }
}
