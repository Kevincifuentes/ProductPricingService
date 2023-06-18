package com.inditex.hiring.infrastructure.persistence.jpa.write;

import com.inditex.hiring.objectmother.domain.OfferMother;
import com.inditex.hiring.objectmother.infrastructure.persistence.jpa.read.model.OfferEntityMother;
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
public class OfferJPAWriterTest {

    @Mock
    private OfferJPARepository offerJPARepository;

    @InjectMocks
    private OfferJPAWriter testSubject;

    @Test
    public void shouldFindAll() {
        // given
        final var offer = OfferMother.random();

        // when
        testSubject.save(offer);

        //then
        final var expectedOfferEntity = OfferEntityMother.from(offer);
        verify(offerJPARepository).save(expectedOfferEntity);
    }
}
