package com.inditex.hiring.application.offer;

import com.inditex.hiring.infrastructure.Application;
import com.inditex.hiring.infrastructure.persistence.jpa.write.OfferJPARepository;
import com.inditex.hiring.objectmother.infrastructure.persistence.jpa.read.model.OfferEntityMother;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DeleteAllOffersIntegrationTest {

    @Autowired
    private DeleteAllOffersHandler testSubject;

    @Autowired
    private OfferJPARepository offerJPARepository;

    @After
    public void tearDown() {
        offerJPARepository.deleteAll();
    }

    @Test
    public void shouldDeleteAllOffers() {
        // given
        final var offerEntity = OfferEntityMother.random();
        offerJPARepository.save(offerEntity);
        final var entityList = offerJPARepository.findAll();
        assertThat(entityList).isNotEmpty();

        //when
        testSubject.execute(new DeleteAllOffersCommand());

        //then
        final var afterEntityList = offerJPARepository.findAll();
        assertThat(afterEntityList).isEmpty();
    }
}
