package com.inditex.hiring.infrastructure.persistence.ports;

import com.inditex.hiring.domain.ports.OfferReader;
import com.inditex.hiring.domain.ports.OfferWriter;
import com.inditex.hiring.infrastructure.persistence.jpa.write.OfferJPARepository;
import com.inditex.hiring.objectmother.domain.OfferMother;
import com.inditex.hiring.objectmother.infrastructure.persistence.jpa.read.model.OfferViewMother;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OfferWriterIntegrationTest {

    @Autowired
    private OfferReader offerReader;

    @Autowired
    private OfferWriter testSubject;

    @Autowired
    private OfferJPARepository offerJPARepository;

    @After
    public void tearDown() {
        offerJPARepository.deleteAll();
    }

    @Test
    public void testSaveOffer() {
        // given
        final var offer = OfferMother.random();

        // when
        testSubject.save(offer);

        // then
        assertThat(offerReader.findAll())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsOnly(OfferViewMother.from(offer));
    }

    @Test
    public void testDeleteOfferById() {
        // given
        final var offer = OfferMother.random();
        testSubject.save(offer);
        assertThat(offerReader.findAll()).hasSize(1);

        // when
        testSubject.deleteById(offer.getId());

        // then
        assertThat(offerReader.findAll()).isEmpty();
    }


}
