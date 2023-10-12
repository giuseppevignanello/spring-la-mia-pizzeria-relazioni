package org.java.app.controller;

import java.util.List;
import java.util.Locale.Category;

import org.java.app.pojo.Ingredient;
import org.java.app.pojo.Pizza;
import org.java.app.serv.IngredientService;
import org.java.app.serv.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

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
	
	@PostMapping("/ingredients_create")
	public String storeIngredient(@Valid @ModelAttribute Ingredient ingredient, 
			BindingResult bindingResult, 
			Model model) {
		
		ingredientService.save(ingredient);
		List <Pizza> pizzas = pizzaService.findAll(); 
		
		for (Pizza pizza: pizzas) {
			if(ingredient.hasPizza(pizza))
				pizza.addIngredient(ingredient); 
			else pizza.removeIngredient(ingredient); 
			
			pizzaService.save(pizza);
		}
			
		return "redirect:/ingredients";
	
	}
	@PostMapping("/ingredients_delete/{id}")
	public String deleteIngredient(@PathVariable int id, Ingredient ingredient, Model model) {
	    List<Ingredient> ingredients = ingredientService.findAll();
	    model.addAttribute("ingredients", ingredients);

	    Ingredient ingredientToDelete = ingredientService.findById(id);

	    if (ingredientToDelete != null) {
	       
	        List<Pizza> pizzas = pizzaService.findAll();
	        for (Pizza pizza : pizzas) {
	            pizza.removeIngredient(ingredientToDelete);
	            pizzaService.save(pizza);
	        }

	      
	        ingredientService.delete(ingredientToDelete);
	    }

	    return "redirect:/ingredients";
	}

}

