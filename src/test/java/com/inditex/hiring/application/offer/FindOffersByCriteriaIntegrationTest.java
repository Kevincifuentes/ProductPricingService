package com.inditex.hiring.application.offer;

import com.inditex.hiring.infrastructure.Application;
import com.inditex.hiring.infrastructure.persistence.jpa.write.OfferEntity;
import com.inditex.hiring.infrastructure.persistence.jpa.write.OfferJPARepository;
import com.inditex.hiring.objectmother.infrastructure.persistence.jpa.read.model.OfferEntityMother;
import com.inditex.hiring.objectmother.infrastructure.persistence.jpa.read.model.OfferViewMother;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class FindOffersByCriteriaIntegrationTest {

    @Autowired
    private FindOffersByCriteriaHandler testSubject;

    @Autowired
    private OfferJPARepository offerJPARepository;

    @After
    public void tearDown() {
        offerJPARepository.deleteAll();
    }

    @Test
    public void shouldFindByCriteria() {
        // given
        final var secondOrderedEntity = insertRandomEntity();
        final var brandId = secondOrderedEntity.getBrandId();
        final var partNumber = secondOrderedEntity.getPartNumber();
        final var thirdOrderedEntity = insertEntity(OfferEntityMother.after(secondOrderedEntity));
        final var firstOrderedEntity = insertEntity(OfferEntityMother.before(secondOrderedEntity));
        final var forthOrderedEntity = insertEntity(OfferEntityMother.after(thirdOrderedEntity));
        final var expectedOfferEntities = List.of(
                firstOrderedEntity, secondOrderedEntity, thirdOrderedEntity, forthOrderedEntity
        );
        final var criteria = OfferCriteria.builder()
                .brandId(brandId)
                .partNumber(partNumber)
                .build();
        final var query = FindOffersByCriteriaQuery.builder()
                .offerCriteria(criteria)
                .shouldSortByStartEndDate(true)
                .build();

        //when
        final var result = testSubject.ask(query);

        //then
        final var expectedViews = expectedOfferEntities.stream()
                .map(OfferViewMother::from)
                .toList();
        assertThat(result)
                .hasSize(expectedViews.size())
                .containsExactlyInAnyOrderElementsOf(expectedViews);
    }

    private OfferEntity insertRandomEntity() {
        final var offerEntity = OfferEntityMother.random();
        return insertEntity(offerEntity);
    }

    private OfferEntity insertEntity(final OfferEntity offerEntity) {
        offerJPARepository.save(offerEntity);
        return offerEntity;
    }
}
