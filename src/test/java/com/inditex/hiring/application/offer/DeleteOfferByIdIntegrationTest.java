package com.inditex.hiring.application.offer;

import com.inditex.hiring.application.cqrs.SpringCommandBus;
import com.inditex.hiring.domain.ports.OfferReader;
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
public class DeleteOfferByIdIntegrationTest {

    @Autowired
    private SpringCommandBus testSubject;

    @Autowired
    private OfferReader offerReader;

    @Autowired
    private OfferJPARepository offerJPARepository;

    @After
    public void tearDown() {
        offerJPARepository.deleteAll();
    }

    @Test
    public void shouldAddOffer() {
        // given
        final var offerEntity = OfferEntityMother.random();
        offerJPARepository.save(offerEntity);
        final var storedOffers = offerReader.findAll();
        assertThat(storedOffers).hasSize(1);
        // and
        final var deleteOfferByIdCommand = new DeleteOfferByIdCommand(offerEntity.getId());


        //when
        testSubject.execute(deleteOfferByIdCommand);

        //then
        assertThat(offerReader.findAll()).isEmpty();
    }
}
