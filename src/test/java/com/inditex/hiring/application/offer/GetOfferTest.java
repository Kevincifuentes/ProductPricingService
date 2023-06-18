package com.inditex.hiring.application.offer;

import com.inditex.hiring.domain.ports.OfferReader;
import com.inditex.hiring.objectmother.infrastructure.persistence.jpa.read.model.OfferViewMother;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.inditex.hiring.TestSuiteUtils.FAKER;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class GetOfferTest {

    @Mock
    private OfferReader offerReader;

    @InjectMocks
    private GetOfferHandler testSubject;

    @Test
    public void shouldAddOffer() {
        // given
        final var getOfferCommand = new GetOfferQuery(FAKER.random().nextLong());
        final var offerId = getOfferCommand.offerId();
        when(offerReader.findById(offerId)).thenReturn(OfferViewMother.random());

        //when
        testSubject.ask(getOfferCommand);

        //then
        verify(offerReader).findById(offerId);
    }
}
