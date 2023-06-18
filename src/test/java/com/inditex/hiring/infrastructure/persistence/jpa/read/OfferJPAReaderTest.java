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

import static com.inditex.hiring.TestSuiteUtils.FAKER;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    public void shouldFind() {
        // given
        final var expectedId = FAKER.random().nextLong();
        final var expectedOfferView = OfferViewMother.random();
        when(offerViewJPARepository.findById(expectedId)).thenReturn(of(OfferViewMother.random()));

        // when
        final var result = testSubject.findById(expectedId);

        //then
        verify(offerViewJPARepository).findById(expectedId);
        assertThat(result).contains(expectedOfferView);
    }
}
