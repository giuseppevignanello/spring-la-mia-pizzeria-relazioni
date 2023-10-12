package org.java.app.controller;

import java.util.List;

import org.java.app.pojo.Ingredient;
import org.java.app.pojo.Pizza;
import org.java.app.serv.IngredientService;
import org.java.app.serv.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
@RequestMapping("/ingredients")
public class IngredientController {
	
	@Autowired
	private PizzaService pizzaService; 
	
	@Autowired
	private IngredientService ingredientService;
	
	@GetMapping 
	public String getIndex(Model model) {
		List <Ingredient> ingredients = ingredientService.findAll();
		System.out.println(ingredients);
		model.addAttribute("ingredients", ingredients);
		return "ingredients_index";
	}

	
	@GetMapping("/ingredients_create") 
	public String getCreate(Model model) {
		List<Pizza> pizzas = pizzaService.findAll(); 
		Ingredient ingredient = new Ingredient(); 
		model.addAttribute("ingredient", ingredient); 
		model.addAttribute("pizzas", pizzas);
		
		return "ingredients_create"; 
	}
}
