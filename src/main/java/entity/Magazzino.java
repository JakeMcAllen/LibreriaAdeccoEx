package entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "magazzino")
public class Magazzino {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "quantita")
	private int quantit�;
	private String statoStock;
	
	
	
	// ERROR: Cannot add or update a child row: a foreign key constraint fails 
	// (`libreriadb`.`magazzino`, CONSTRAINT `fk_Magazzino_Libri` FOREIGN KEY (`isbn`) REFERENCES `libri` (`isbn`))
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "isbn")
	private Libro isbn;
	
	
	
	public Magazzino() {}

	public Magazzino(int quantit�, String statoStock, Libro isbn) {
		this.quantit� = quantit�;
		this.statoStock = statoStock;
		this.isbn = isbn;
	}

	
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public int getQuantit�() { return quantit�; }
	public void setQuantit�(int quantit�) { this.quantit� = quantit�; }

	public String getStatoStock() { return statoStock; }
	public void setStatoStock(String statoStock) { this.statoStock = statoStock; }
	
	public Libro getIsbn() { return isbn; }
	public void setIsbn(Libro isbn) { this.isbn = isbn; }


	
	@Override
	public String toString() {
		return "Magazzino [id=" + id + ", quantit�=" + quantit� + ", statoStock=" + statoStock + ", isbn=" + isbn + "]";
	}
	
}
