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
	
	,
	@NamedQuery(name="product.getLastNProducts",
	query = "from product d order by d.date desc"
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
	
	@Column(name = "quantityAvailable")
	private Integer quantityAvailable;
	
	
	@Column(name = "newOrReproduced")
	private Integer newOrReproduced;
	
	
	@Column(name = "forGender")
	private Integer forGender;
	
	@Column(name = "rawMaterialCostTotal")
	private Float rawMaterialCostTotal;
	
	
	@Column(name = "recommendedmarkUp")
	private Float recommendedmarkUp;
	
	@Column(name = "recommendedSalePrice")
	private Float recommendedSalePrice;
	
	@Column(name = "description")
	private String description;
	

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

	
	@Column(name = "lastUpdate")
	private Calendar lastUpdate;

	

	public Calendar getLastUpdate() {
		return lastUpdate;
	}





	public void setLastUpdate(Calendar lastUpdate) {
		this.lastUpdate = lastUpdate;
	}





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





	public Integer getQuantityAvailable() {
		return quantityAvailable;
	}





	public void setQuantityAvailable(Integer quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}





	







	public Float getRawMaterialCostTotal() {
		return rawMaterialCostTotal;
	}





	public void setRawMaterialCostTotal(Float rawMaterialCostTotal) {
		this.rawMaterialCostTotal = rawMaterialCostTotal;
	}





	public Float getRecommendedmarkUp() {
		return recommendedmarkUp;
	}





	public void setRecommendedmarkUp(Float recommendedmarkUp) {
		this.recommendedmarkUp = recommendedmarkUp;
	}





	public Float getRecommendedSalePrice() {
		return recommendedSalePrice;
	}





	public void setRecommendedSalePrice(Float recommendedSalePrice) {
		this.recommendedSalePrice = recommendedSalePrice;
	}





	public String getDescription() {
		return description;
	}





	public void setDescription(String description) {
		this.description = description;
	}





	public byte[] getImage() {
		return image;
	}





	public void setImage(byte[] image) {
		this.image = image;
	}


	public static int SIZE_SMALL=1;
	public static int SIZE_MEDUIM=2;
	public static int SIZE_LARGE=3;
	public static int SIZE_X_LARGE=4;
	public static int SIZE_XX_LARGE=5;
	public static int SIZE_XXX_LARGE=6;
	public static int SIZE_XXXX_LARGE=7;
	public String getSizeString() {
		if(size==1) {
			return "S";
		}else if(size==2) {
			return "M";
		}else if(size==3) {
			return "L";
		}else if(size==4) {
			return "XL";
		}else if(size==5) {
			return "XXL";
		}else if(size==6) {
			return "XXXL";
		}else {
			return "XXXXL";
		}
	}
	

	public static int GENDER_MALE=1;
	public static int GENDER_FEMALE=2;
	public static int GENDER_MALE_YOUNG=3;
	public static int GENDER_FEMALE_YOUNG=4;
	public String getForGenderString() {
		if(size==1) {
			return "Male";
		}else if(size==2) {
			return "Female";
		}else if(size==3) {
			return "Male young";
		}else {
			return "Female young";
		}
	}
	

	public static int NEW_TYPE=1;
	public static int REPRODUCED_TYPE=2;
	public String getNewOrReproducedString() {
		if(newOrReproduced==1) {
			return "New";
		}else  {
			return "Reproduced";
		}
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
