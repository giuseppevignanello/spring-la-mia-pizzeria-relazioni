package org.java.app.serv;

import java.util.List;

import org.java.app.pojo.Pizza;
import org.java.app.repo.PizzaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {

	@Autowired
	private PizzaRepo pizzaRepo;

	public void save(Pizza pizza) {

		pizzaRepo.save(pizza);
	}

	public List<Pizza> findAll() {

		return pizzaRepo.findAll();
	}

	public Pizza findById(int id) {

		return pizzaRepo.findById(id).get();
	}



	public List<Pizza> filterByName(String pizzaName, String pizzaDescription) {
		
		return pizzaRepo.findByNameContainingOrDescriptionContaining(pizzaName, pizzaDescription);
	}
	
	public void deletePizza(Pizza pizza) {
		pizzaRepo.delete(pizza);
	}
}
