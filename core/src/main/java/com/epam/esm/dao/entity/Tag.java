package com.epam.esm.dao.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entity class for creating {@code Tag} objects. */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tag")
public class Tag implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Exclude
	private long id;

	@Column(name = "name")
	private String name;

	public Tag(String name) {
		this.name = name;
	}

	@ManyToMany(mappedBy = "tags")
	private Set<Certificate> certificates = new HashSet<>();

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Tag tag = (Tag) o;

		return name.equals(tag.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " {id=" + id + ", name='" + name + "\'}";
	}
}
