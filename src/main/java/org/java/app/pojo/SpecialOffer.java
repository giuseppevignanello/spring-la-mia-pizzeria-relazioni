package org.java.app.pojo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class SpecialOffer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private LocalDate startDate;
	
	@Column(nullable = false)
	private LocalDate endDate;
	
	@Column(unique = true, length = 30, nullable = false)
	@NotBlank(message = "Il titolo dell'offerta non può essere vuoto")
	@Length(
			max = 30,
			message = "Il titolo non può essere più lungo di 30 caratteri"
		)
	private String title;
	
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Pizza pizza;
	
	public SpecialOffer() {}; 
	public SpecialOffer(LocalDate startDate, LocalDate endDate, String title, Pizza pizza) {
		setStartDate(startDate); 
		setEndDate(endDate); 
		setTitle(title); 
		setPizza(pizza);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}
	
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle (String title) {
		this.title = title;
	}
	
	public Pizza getPizza() {
		return pizza;
	}
	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}
	
	public String getHTMLStartDate() {
		return getStartDate() == null ? null : getStartDate().format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
	}
	public void setHTMLStartDate(String date) {
		setStartDate(LocalDate.parse(date));
	}
	
	public String getHTMLEndDate() {
		return getEndDate() == null ? null : getEndDate().format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
	}
	public void setHTMLEndDate(String date) {
		setEndDate(LocalDate.parse(date));
	}
}

