package com.inditex.hiring.infrastructure.persistence.ports;

import com.inditex.hiring.application.offer.OfferCriteria;
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

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OfferReaderIntegrationTest {

    @Autowired
    private OfferReader testSubject;

    @Autowired
    private OfferWriter offerWriter;

    @Autowired
    private OfferJPARepository offerJPARepository;

    @After
    public void tearDown() {
        offerJPARepository.deleteAll();
    }

    @Test
    public void testFindById() {
        // given
        final var offer = OfferMother.random();
        offerWriter.save(offer);
        final var offers = testSubject.findAll();
        assertThat(offers).hasSize(1);
        final var storedOffer = offers.get(0);

        // when
        final var result = testSubject.findById(storedOffer.getId());

        // then
        assertThat(result).contains(storedOffer);
    }

    @Test
    public void testFindByCriteria() {
        // given
        final var offer = OfferMother.random();
        offerWriter.save(offer);
        final var offerBeforeLast = OfferMother.afterNewOffer(offer);
        final var lastOffer = OfferMother.afterNewOffer(offerBeforeLast);
        offerWriter.save(lastOffer);
        offerWriter.save(offerBeforeLast);
        final var offers = testSubject.findAll();
        assertThat(offers).hasSize(3);
        // and
        final var offerCriteria = OfferCriteria.builder()
                .brandId(offer.getBrandId())
                .partNumber(offer.getPartNumber())
                .shouldSortByStartEndDate(true)
                .build();

        // when
        final var result = testSubject.findByCriteria(offerCriteria);

        // then
        final var expectedOfferViewsWithOrder = Stream.of(
                offer, offerBeforeLast, lastOffer
        )
                .map(OfferViewMother::from)
                .toList();
        assertThat(result).hasSize(3)
                .containsExactlyElementsOf(expectedOfferViewsWithOrder);
    }
}
