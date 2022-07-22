package com.epam.esm.dao.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "purchase")
public class Purchase implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "user_id")
	private long userId;

	@Column(name = "date")
	private LocalDateTime date;

	@Column(name = "cost")
	private int cost;

	@ManyToMany
	@JoinTable(name = "purchase_gift_certificate", joinColumns = @JoinColumn(name = "purchase_id"), inverseJoinColumns = @JoinColumn(name = "gift_certificate_id"))
	Set<Certificate> certificates = new HashSet<>();

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Purchase purchase = (Purchase) o;

		if (userId != purchase.userId)
			return false;
		if (cost != purchase.cost)
			return false;
		return date.equals(purchase.date);
	}

	@Override
	public int hashCode() {
		int result = (int) (userId ^ (userId >>> 32));
		result = 31 * result + date.hashCode();
		result = 31 * result + cost;
		return result;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{" + "id=" + id + ", userId=" + userId + ", date=" + date + ", cost="
				+ cost + '}';
	}
}
