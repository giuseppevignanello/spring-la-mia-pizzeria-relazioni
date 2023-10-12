package org.java.app.controller;



import java.util.List;

import org.hibernate.internal.build.AllowSysOut;
import org.java.app.pojo.Ingredient;
import org.java.app.pojo.Pizza;
import org.java.app.pojo.SpecialOffer;
import org.java.app.repo.IngredientRepo;
import org.java.app.serv.IngredientService;
import org.java.app.serv.OfferService;
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
@RequestMapping("/")
public class PizzaContoller {
	
	@Autowired
	private PizzaService pizzaService;
	@Autowired
	private OfferService offerService;
	@Autowired
	private IngredientService ingredientService;
	
	@GetMapping("/")
	public String getIndex(Model model, @RequestParam(required= false) String name) {
		List<Pizza> pizzas = name == null ? pizzaService.findAll() : pizzaService.filterByName(name, name);
		model.addAttribute("pizzas", pizzas);
		return "index";
	}
	
	@GetMapping("/{id}")
	public String show(Model model, @PathVariable int id) {
		
		Pizza pizza = pizzaService.findById(id);
		 List<SpecialOffer> specialOffers = pizza.getSpecialOffers(); 
		 List<Ingredient> ingredients = pizza.getIngredients();
		model.addAttribute("pizza", pizza);	
		 model.addAttribute("specialOffers", specialOffers);
		 model.addAttribute("ingredients", ingredients);
		return "show";
	}
	
	@GetMapping("/create")
	public String getCreateForm(Model model) {
		
		List<Ingredient> ingredients = ingredientService.findAll();
		model.addAttribute("pizza", new Pizza());
		model.addAttribute("ingredients", ingredients);
		return "create";
	}
	
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute Pizza pizza,
			BindingResult bindingResult, Model model) {
		
		List<Pizza> pizzas = pizzaService.findAll();
		model.addAttribute("pizzas", pizzas);
		
		if(bindingResult.hasErrors()) {
			System.out.println("Error: ");
			bindingResult.getAllErrors().forEach(System.out::println);
			return "create";
		}
		
		try {
			pizzaService.save(pizza);
			List<Ingredient> ingredients = ingredientService.findAll();
			for (Ingredient ingredient: ingredients) {
				if(pizza.hasIngredient(ingredient))
					ingredient.addPizzas(pizza);
				else ingredient.removePizzas(pizza);
				
				ingredientService.save(ingredient);
			}
		} catch(Exception e) {
			System.out.println("Errors: " + e.getClass().getSimpleName());
			return "create";
		}
		return "index";
	
		
	}
	

	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		List<Ingredient> ingredients = ingredientService.findAll();
		model.addAttribute("pizza", pizzaService.findById(id));
		model.addAttribute("ingredients", ingredients);
		return "edit";
	}
	
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute Pizza pizza, BindingResult bindingResult, Model model) {
		
		List<Pizza> pizzas = pizzaService.findAll();
		model.addAttribute("pizzas", pizzas);
		
		if(bindingResult.hasErrors()) {
			System.out.println("Error: ");
			bindingResult.getAllErrors().forEach(System.out::println);
			return "edit";
		}
		
		try {
			pizzaService.save(pizza);
		} catch(Exception e) {
			System.out.println("Errors: " + e.getClass().getSimpleName());
			return "edit";
		}
		return "index";
		
	}
	
	@PostMapping("/delete/{id}")
	
	
	public String delete(@PathVariable int id, Pizza pizza, Model model) {
		
		List<Pizza> pizzas = pizzaService.findAll();
		model.addAttribute("pizzas", pizzas);
		
		Pizza pizzaToDelete = pizzaService.findById(id); 
		pizzaService.deletePizza(pizzaToDelete);
		return "redirect:/";
	}
	
	@GetMapping("/createOffer/{pizza_id}")
	public String offer(
			@PathVariable("pizza_id") int id, Model model
			) {
		
		Pizza pizza = pizzaService.findById(id);
		SpecialOffer specialOffer = new SpecialOffer();
		model.addAttribute("pizza", pizza); 
		model.addAttribute("specialOffer", specialOffer);
				return "createOffer";
		
	}
	
	@PostMapping("/createOffer/{pizza_id}")
	public String storeOffer(@Valid @ModelAttribute SpecialOffer specialOffer, 
			BindingResult bindingResult, 
			@PathVariable("pizza_id") int id, 
			Model model
	) {
		
		Pizza pizza = pizzaService.findById(id); 
		specialOffer.setPizza(pizza); 
		
		offerService.save(specialOffer);
		
		return "redirect:/" + id;
		
	}
	
	@GetMapping("/editOffer/{special_offer_id}")
	public String editOffer(@PathVariable("special_offer_id") int id, Model model) {
		
		SpecialOffer specialOffer = offerService.findById(id);
		List<Pizza> pizzas = pizzaService.findAll();
		
		model.addAttribute("specialOffer", specialOffer);
		model.addAttribute("pizzas", pizzas);
		return "editOffer";
	}
	@PostMapping("/editOffer/{special_offer_id}")
	public String updateOffer (@Valid @ModelAttribute SpecialOffer specialOffer, 
			BindingResult bindingResult) {
		Pizza pizzaWithOffer = specialOffer.getPizza();
		
		offerService.save(specialOffer);
		
	
		
		return "redirect:/ " + pizzaWithOffer.getId();
		
	}

	
}
