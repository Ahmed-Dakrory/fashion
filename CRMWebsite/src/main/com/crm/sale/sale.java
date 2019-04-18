package main.com.crm.sale;

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

import main.com.crm.customer.customer;
import main.com.crm.loginNeeds.user;
import main.com.crm.product.product;


/**
 * 
 * @author Ahmed.Dakrory
 *
 */


@NamedQueries({
	
	
	@NamedQuery(name="sale.getAll",
		     query="SELECT c FROM sale c"
		     )
	,
	@NamedQuery(name="sale.getById",
	query = "from sale d where d.id = :id"
			)
	
	
	
})

@Entity
@Table(name = "sale")
public class sale {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	
	@Column(name = "price")
	private String price;
	
	@Column(name = "quantity")
	private Integer quantity;
	
	
	@Column(name = "type")
	private Integer type;
	

	@ManyToOne
	@JoinColumn(name = "product_id")
	private product product_id;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private customer customer_id;
	
	
	@Column(name = "date")
	private Calendar date;
	
	
	@ManyToOne
	@JoinColumn(name = "addedByUser_id")
	private user addedByUser_id;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public product getProduct_id() {
		return product_id;
	}


	public void setProduct_id(product product_id) {
		this.product_id = product_id;
	}


	public customer getCustomer_id() {
		return customer_id;
	}


	public void setCustomer_id(customer customer_id) {
		this.customer_id = customer_id;
	}


	public Calendar getDate() {
		return date;
	}


	public void setDate(Calendar date) {
		this.date = date;
	}


	public user getAddedByUser_id() {
		return addedByUser_id;
	}


	public void setAddedByUser_id(user addedByUser_id) {
		this.addedByUser_id = addedByUser_id;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	
	
	

}
