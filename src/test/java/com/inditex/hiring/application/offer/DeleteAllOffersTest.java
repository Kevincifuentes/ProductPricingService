package com.inditex.hiring.application.offer;

import com.inditex.hiring.domain.ports.OfferWriter;
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
public class DeleteAllOffersTest {

    @Mock
    private OfferWriter offerWriter;

    @InjectMocks
    private DeleteAllOffersHandler testSubject;

    @Test
    public void shouldAddOffer() {
        // given
        final var deleteAllOffersCommand = new DeleteAllOffersCommand();

        //when
        testSubject.execute(deleteAllOffersCommand);

        //then
        verify(offerWriter).deleteAll();
    }
}
