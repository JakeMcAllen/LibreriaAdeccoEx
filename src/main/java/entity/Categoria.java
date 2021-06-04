package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categorialibri")
public class Categoria {

	@Id
	private String categoria;
	
	public Categoria() {}
	
	public Categoria(String categoria) {
		this.categoria = categoria;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	
	@Override
	public String toString() {
		return "Categoria [categoria=" + categoria + "]";
	}
	
}
