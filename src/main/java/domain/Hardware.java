package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@Entity
@NamedQueries({
	@NamedQuery(name="hardware.all", query="SELECT h from Hardware h"),
	@NamedQuery(name="hardware.id", query="FROM Hardware h WHERE h.id=:hardwareId"),
	@NamedQuery(name="hardware.price", query="SELECT DISTINCT h FROM Hardware h WHERE h.priceMin>=:hardwarePriceMin AND h.priceMax<=:hardwarePriceMax"),
	@NamedQuery(name="hardware.name", query="SELECT DISTINCT h FROM Hardware h WHERE h.name=:hardwareName"),
	@NamedQuery(name="hardware.category", query="FROM Hardware h WHERE h.category=:hardwareCategory")
})
public class Hardware {
	
	private int id;
	private String name;
	private String category;
	private float priceMin;
	private float priceMax;
	private List<Comment> comment = new ArrayList<Comment>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public float getPriceMin() {
		return priceMin;
	}
	public void setPriceMin(float priceMin) {
		this.priceMin = priceMin;
	}
	public float getPriceMax() {
		return priceMax;
	}
	public void setPriceMax(float priceMax) {
		this.priceMax = priceMax;
	}
	@XmlTransient
	@OneToMany(mappedBy="hardware")
	public List<Comment> getComment() {
		return comment;
	}
	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}
	
	
	
	
}
