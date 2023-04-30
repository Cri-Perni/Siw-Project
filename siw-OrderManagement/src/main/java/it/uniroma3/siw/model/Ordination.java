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
	
	@ManyToOne
	private Sale sale;

	public Ordination(boolean isPaid, Integer tableNumber, Float total){
		this.id = null;
		this.items = null;
		this.sale = null;
		this.isPaid= isPaid;
		this.tableNumber = tableNumber;
		this.total = total;
	}

	public Ordination() {
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
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

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public Integer getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(Integer tableNumber) {
		this.tableNumber = tableNumber;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, items, tableNumber, sale, total);
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
				&& Objects.equals(tableNumber, other.tableNumber) && Objects.equals(sale, other.sale)
				&& Objects.equals(total, other.total);
	}
	
	

}
