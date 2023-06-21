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

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class FindAllOffersTest {

    @Mock
    private OfferReader offerReader;

    @InjectMocks
    private FindAllOffersHandler testSubject;

    @Test
    public void shouldFindAllOffers() {
        // given
        final var findAllOffersQuery = new FindAllOffersQuery();
        final var expectedOfferViews = of(OfferViewMother.random());
        when(offerReader.findAll()).thenReturn(expectedOfferViews);

        //when
        final var result = testSubject.ask(findAllOffersQuery);

        //then
        verify(offerReader).findAll();
        assertThat(result).isEqualTo(expectedOfferViews);
    }
}
