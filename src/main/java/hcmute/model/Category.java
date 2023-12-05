package hcmute.model;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.*;

import hcmute.model.audit.UserDateAudit;
import hcmute.utils.Service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Category extends UserDateAudit {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false, length = 50)
	private String name;
	
	@OneToMany(mappedBy = "category",cascade = CascadeType.ALL,
			orphanRemoval = true)
<<<<<<< HEAD
	private List<Laptop> laptops;

	@OneToOne
	@JoinColumn(name = "image_id", referencedColumnName = "id")
	private Image image;

	public List<Laptop> getLaptops() {
		return laptops == null ? null : new ArrayList<Laptop>(this.laptops);
	}

	public void setLaptops(List<Laptop> laptops) {
		if(laptops == null) {
			this.laptops = null;
		}
		this.laptops = laptops;
=======
	private List<Book> books;

	@OneToOne
	@JoinColumn(name = "image_id", referencedColumnName = "id")
	private Image image;

	public List<Book> getBooks() {
		return books == null ? null : new ArrayList<Book>(this.books);
	}

	public void setBooks(List<Book> books) {
		if(books == null) {
			this.books = null;
		}
		this.books = books;
>>>>>>> branch 'master' of https://github.com/quangnghia1110/doancuoiky.git
	}
	
	public String getDomain(){
		return Service.removeAccent(name);
	}
	
	
}
