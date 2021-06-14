package com.springboot.app.users.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.springboot.app.users.model.Cart;

@Entity
@Table(name = "carritos")
public class Cart implements Serializable {

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<CartItem> items = new HashSet<>();
	

	
	public Cart() {
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Set<CartItem> getItems() {
		return items;
	}


	public void setItems(Set<CartItem> items) {
		this.items = items;
	}


	


	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cart other = (Cart) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", items=" + items + "]";
	}






	/**
	 * 
	 */
	private static final long serialVersionUID = 6079073354654371871L;
	
}
