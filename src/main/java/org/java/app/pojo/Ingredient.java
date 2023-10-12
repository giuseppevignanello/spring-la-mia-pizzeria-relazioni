package org.java.app.pojo;

import java.util.Arrays;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Ingredient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true, length= 30, nullable = false)
	@NotBlank(message = "Il nome dell'ingrediente non può essere vuoto")
	@Length(
			max = 30,
			message = "Il nome non può essere più lungo di 30 caratteri"
		)
	private String name; 
	
	public Ingredient() {
	} 
	public Ingredient(String name) {
		setName(name);
		setId(id);
	}
	
	@ManyToMany(mappedBy = "ingredients")
	private List<Pizza> pizzas;
	
	public int getId() {
		return id; 
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name; 
	}
	public void setName(String name) {
		this.name = name;
		
	}
	
	public List<Pizza> getPizzas() {
		return pizzas;
	}
	
	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}
	
	public boolean hasPizza(Pizza pizza) {
		if(getPizzas() == null) return false; 
		for (Pizza p : getPizzas()) 
			if (p.getId() == pizza.getId()) 
				return true;
		return false; 
		
	} 
	
	public void addPizzas (Pizza ...pizzas) {
		getPizzas().addAll(Arrays.asList(pizzas));
	}
	public void removePizzas(Pizza ...pizzas) {
		getPizzas().removeAll(Arrays.asList(pizzas));
		
	}
	
	@Override
	public String toString() {
		
		return "[" + getId() + "] " + getName();
	}
	
}
