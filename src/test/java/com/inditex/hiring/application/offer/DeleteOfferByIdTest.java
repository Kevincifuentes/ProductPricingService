package com.inditex.hiring.application.offer;

import com.inditex.hiring.domain.ports.OfferWriter;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.inditex.hiring.TestSuiteUtils.FAKER;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class DeleteOfferByIdTest {

    @Mock
    private OfferWriter offerWriter;

    @InjectMocks
    private DeleteOfferByIdHandler testSubject;

    @Test
    public void shouldDeleteOfferById() {
        // given
        final var expectedOfferId = FAKER.number().randomNumber();
        final var deleteOfferByIdCommand = new DeleteOfferByIdCommand(expectedOfferId);

        //when
        testSubject.execute(deleteOfferByIdCommand);

        //then
        verify(offerWriter).deleteById(expectedOfferId);
    }
}
