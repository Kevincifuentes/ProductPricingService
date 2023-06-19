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
import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class FindOffersByCriteriaTest {

    @Mock
    private OfferReader offerReader;

    @InjectMocks
    private FindOffersByCriteriaHandler testSubject;

    @Test
    public void shouldFindOfferByCriteria() {
        // given
        final var brandId = FAKER.number().randomDigit();
        final var partNumber = FAKER.streetFighter().moves();
        final var criteria = OfferCriteria.builder()
                .brandId(brandId)
                .partNumber(partNumber)
                .build();
        final var findOffersByCriteriaQuery = FindOffersByCriteriaQuery.builder()
                .offerCriteria(criteria)
                .shouldSortByStartEndDate(true)
                .build();
        final var expectedOfferViews = of(OfferViewMother.random());
        when(offerReader.findByCriteria(criteria)).thenReturn(expectedOfferViews);

        //when
        final var result = testSubject.ask(findOffersByCriteriaQuery);

        //then
        verify(offerReader).findByCriteria(criteria);
        assertThat(result).isEqualTo(expectedOfferViews);
    }
}
