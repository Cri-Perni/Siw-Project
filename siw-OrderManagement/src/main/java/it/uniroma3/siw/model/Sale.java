package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Sale {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	

	private LocalDate date;

	private LocalTime time;
	
	private Float Total;
	
	@OneToMany(mappedBy="sale")
	private List<Ordination> orders;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public Float getTotal() {
		return Total;
	}

	public void setTotal(Float total) {
		Total = total;
	}

	public List<Ordination> getOrders() {
		return orders;
	}

	public void setOrders(List<Ordination> orders) {
		this.orders = orders;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Total, date, id, orders, time);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sale other = (Sale) obj;
		return Objects.equals(Total, other.Total) && Objects.equals(date, other.date) && Objects.equals(id, other.id)
				&& Objects.equals(orders, other.orders) && Objects.equals(time, other.time);
	}
	

}
