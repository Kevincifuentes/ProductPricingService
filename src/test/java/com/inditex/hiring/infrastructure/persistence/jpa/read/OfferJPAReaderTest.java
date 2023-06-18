package com.inditex.hiring.infrastructure.persistence.jpa.read;

import com.inditex.hiring.objectmother.infrastructure.persistence.jpa.read.model.OfferViewMother;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class OfferJPAReaderTest {

    @Mock
    private OfferViewJPARepository offerViewJPARepository;

    @InjectMocks
    private OfferJPAReader testSubject;

    @Test
    public void shouldFindAll() {
        // given
        final var expectedOfferViews = List.of(OfferViewMother.random());
        when(offerViewJPARepository.findAll()).thenReturn(expectedOfferViews);

        // when
        testSubject.findAll();

        //then
        verify(offerViewJPARepository).findAll();
    }
}
