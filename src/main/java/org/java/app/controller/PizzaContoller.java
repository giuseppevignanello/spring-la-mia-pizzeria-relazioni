package org.java.app.controller;



import java.util.List;

import org.hibernate.internal.build.AllowSysOut;
import org.java.app.pojo.Pizza;
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
	
	@GetMapping("/")
	public String getIndex(Model model, @RequestParam(required= false) String name) {
		List<Pizza> pizzas = name == null ? pizzaService.findAll() : pizzaService.filterByName(name, name);
		model.addAttribute("pizzas", pizzas);
		return "index";
	}
	
	@GetMapping("/{id}")
	public String show(Model model, @PathVariable int id) {
		
		Pizza pizza = pizzaService.findById(id);
		model.addAttribute("pizza", pizza);	
		return "show";
	}
	
	@GetMapping("/create")
	public String getCreateForm(Model model) {
		
		model.addAttribute("pizza", new Pizza());
		
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
		} catch(Exception e) {
			System.out.println("Errors: " + e.getClass().getSimpleName());
			return "create";
		}
		return "index";
	
		
	}
	

	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		model.addAttribute("pizza", pizzaService.findById(id));
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

}
