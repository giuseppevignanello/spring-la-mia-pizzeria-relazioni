package org.java.app.serv;


import org.java.app.pojo.SpecialOffer;
import org.java.app.repo.OfferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferService {

	
	@Autowired
	private OfferRepo offerRepo;

	public void save(SpecialOffer specialOffer) {

		offerRepo.save(specialOffer);
	}

	public SpecialOffer findById(int id) {
		return offerRepo.findById(id).get();
	}
}
