package com.inditex.hiring.infrastructure.persistence.jpa.read;

import com.inditex.hiring.infrastructure.persistence.jpa.read.model.OfferView;
import com.inditex.hiring.infrastructure.persistence.ports.OfferReader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class OfferJPAReader implements OfferReader {

    private final OfferViewJPARepository offerViewJPARepository;

    @Override
    public List<OfferView> findAll() {
        return offerViewJPARepository.findAll();
    }
}
