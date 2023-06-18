package com.inditex.hiring.infrastructure.persistence.ports;

import com.inditex.hiring.objectmother.domain.OfferMother;
import com.inditex.hiring.objectmother.infrastructure.persistence.jpa.read.model.OfferViewMother;
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


}
