package main.com.crm.productMaterials;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import main.com.crm.product.product;
import main.com.crm.rawMaterial.rawMaterial;


/**
 * 
 * @author Ahmed.Dakrory
 *
 */


@NamedQueries({
	
	
	@NamedQuery(name="productMaterials.getAll",
		     query="SELECT c FROM productMaterials c"
		     )
	,
	@NamedQuery(name="productMaterials.getById",
	query = "from productMaterials d where d.id = :id"
			)
	,
	@NamedQuery(name="productMaterials.getByProductId",
	query = "from productMaterials d where d.product_id.id = :id"
			)
	
	
	
})

@Entity
@Table(name = "productMaterials")
public class productMaterials {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private product product_id;
	
	
	@ManyToOne
	@JoinColumn(name = "rawMaterial_id")
	private rawMaterial rawMaterial_id;


	@Column(name = "quantityUsedByThisProduct")
	private Integer quantityUsedByThisProduct;
	
	
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


	public product getProduct_id() {
		return product_id;
	}


	public void setProduct_id(product product_id) {
		this.product_id = product_id;
	}


	public rawMaterial getRawMaterial_id() {
		return rawMaterial_id;
	}


	public void setRawMaterial_id(rawMaterial rawMaterial_id) {
		this.rawMaterial_id = rawMaterial_id;
	}


	public Integer getQuantityUsedByThisProduct() {
		return quantityUsedByThisProduct;
	}


	public void setQuantityUsedByThisProduct(Integer quantityUsedByThisProduct) {
		this.quantityUsedByThisProduct = quantityUsedByThisProduct;
	}
	

	
	
}
