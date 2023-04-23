package it.uniroma3.siw.model;

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
public class Ordination {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Integer tableNumber;
	
	private Float total;
	
	private Boolean isPaid;
	
	@OneToMany(mappedBy="order")
	List<OrderItem> items;
	
	@OneToMany(mappedBy="order")
	private List<Sale> sales;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumberTable() {
		return tableNumber;
	}

	public Boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

	public void setNumberTable(Integer numberTable) {
		this.tableNumber = numberTable;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public List<Sale> getSale() {
		return sales;
	}

	public void setSale(List<Sale> sales) {
		this.sales = sales;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, items, tableNumber, sales, total);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ordination other = (Ordination) obj;
		return Objects.equals(id, other.id) && Objects.equals(items, other.items)
				&& Objects.equals(tableNumber, other.tableNumber) && Objects.equals(sales, other.sales)
				&& Objects.equals(total, other.total);
	}
	
	

}
