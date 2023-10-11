package org.java.app.repo;


import org.java.app.pojo.SpecialOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepo extends JpaRepository <SpecialOffer, Integer> {

}
