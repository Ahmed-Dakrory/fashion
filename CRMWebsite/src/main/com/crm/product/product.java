package main.com.crm.product;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import main.com.crm.loginNeeds.user;


/**
 * 
 * @author Ahmed.Dakrory
 *
 */


@NamedQueries({
	
	
	@NamedQuery(name="product.getAll",
		     query="SELECT c FROM product c"
		     )
	,
	@NamedQuery(name="product.getById",
	query = "from product d where d.id = :id"
			)
	
})

@Entity
@Table(name = "product")
public class product {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "size")
	private Integer size;
	
	@Column(name = "quantity")
	private Integer quantity;
	
	
	@Column(name = "newOrReproduced")
	private Integer newOrReproduced;
	
	
	@Column(name = "forGender")
	private Integer forGender;
	
	@Column(name = "suppliesCostPerUnit")
	private Integer suppliesCostPerUnit;
	
	@Column(name = "productionCostPerUnit")
	private Integer productionCostPerUnit;
	
	@Column(name = "overHeadCostPerUnit")
	private Integer overHeadCostPerUnit;
	
	@Column(name = "recommendedmarkUp")
	private Integer recommendedmarkUp;
	
	@Column(name = "recommendedSalePrice")
	private Integer recommendedSalePrice;
	

	@ManyToOne
	@JoinColumn(name = "addedByUser_id")
	private user addedByUser_id;
	
	@Column(name = "producedBy")
	private String producedBy;
	
	@Column(name = "date")
	private Calendar date;
	
	
	@Column(name="image")
	@Lob
	private byte[] image;

	
	


	public Integer getId() {
		return id;
	}





	public void setId(Integer id) {
		this.id = id;
	}





	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}





	public Integer getSize() {
		return size;
	}





	public void setSize(Integer size) {
		this.size = size;
	}





	public Integer getQuantity() {
		return quantity;
	}





	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}





	




	public Integer getNewOrReproduced() {
		return newOrReproduced;
	}





	public void setNewOrReproduced(Integer newOrReproduced) {
		this.newOrReproduced = newOrReproduced;
	}





	public Integer getForGender() {
		return forGender;
	}





	public void setForGender(Integer forGender) {
		this.forGender = forGender;
	}





	public Integer getSuppliesCostPerUnit() {
		return suppliesCostPerUnit;
	}





	public void setSuppliesCostPerUnit(Integer suppliesCostPerUnit) {
		this.suppliesCostPerUnit = suppliesCostPerUnit;
	}





	public Integer getProductionCostPerUnit() {
		return productionCostPerUnit;
	}





	public void setProductionCostPerUnit(Integer productionCostPerUnit) {
		this.productionCostPerUnit = productionCostPerUnit;
	}





	public Integer getOverHeadCostPerUnit() {
		return overHeadCostPerUnit;
	}





	public void setOverHeadCostPerUnit(Integer overHeadCostPerUnit) {
		this.overHeadCostPerUnit = overHeadCostPerUnit;
	}





	public Integer getRecommendedmarkUp() {
		return recommendedmarkUp;
	}





	public void setRecommendedmarkUp(Integer recommendedmarkUp) {
		this.recommendedmarkUp = recommendedmarkUp;
	}





	public Integer getRecommendedSalePrice() {
		return recommendedSalePrice;
	}





	public void setRecommendedSalePrice(Integer recommendedSalePrice) {
		this.recommendedSalePrice = recommendedSalePrice;
	}





	public user getAddedByUser_id() {
		return addedByUser_id;
	}





	public void setAddedByUser_id(user addedByUser_id) {
		this.addedByUser_id = addedByUser_id;
	}





	public String getProducedBy() {
		return producedBy;
	}





	public void setProducedBy(String producedBy) {
		this.producedBy = producedBy;
	}





	public Calendar getDate() {
		return date;
	}





	public void setDate(Calendar date) {
		this.date = date;
	}





	public byte[] getImage() {
		return image;
	}





	public void setImage(byte[] image) {
		this.image = image;
	}


	


	public String getphoto() {
		if(getImage()!=null) {
		String imageString= new String(Base64.encodeBase64(image));

		return "data:image/png;base64, "+imageString;
		}else {
			return "/resources/images/blankImage.svg";
		}
	}

}
