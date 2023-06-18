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
public class FindAllOffersIntegrationTest {

    @Autowired
    private FindAllOffersHandler testSubject;

    @Autowired
    private OfferJPARepository offerJPARepository;

    @After
    public void tearDown() {
        offerJPARepository.deleteAll();
    }

    @Test
    public void shouldFindAll() {
        // given
        final var expectedOfferEntities = List.of(
                insertEntity(), insertEntity()
        );
        final var query = new FindAllOffersQuery();

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

    private OfferEntity insertEntity() {
        final var offerEntity = OfferEntityMother.random();
        offerJPARepository.save(offerEntity);
        return offerEntity;
    }
}
