package com.inditex.hiring.infrastructure.persistence.jpa.read;

import com.inditex.hiring.infrastructure.persistence.jpa.read.model.OfferView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferViewJPARepository extends JpaRepository<OfferView, Long> {

}
