package org.java.app.pojo;

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
	
	private int getId() {
		return id; 
	}
	
	private void setId(int id) {
		this.id = id;
	}
	private String getName() {
		return name; 
	}
	private void setName(String name) {
		this.name = name;
		
	}
	

	@Override
	public String toString() {
		
		return "[" + getId() + "] " + getName();
	}
	
}
