package com.inditex.hiring.infrastructure.persistence;

import com.inditex.hiring.infrastructure.persistence.ports.OfferReader;
import com.inditex.hiring.infrastructure.persistence.ports.OfferWriter;
import com.inditex.hiring.objectmother.domain.OfferMother;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

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
                .containsOnly(offer);
    }


}
