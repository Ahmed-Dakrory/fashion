package main.com.crm.expense;

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

import main.com.crm.loginNeeds.user;


/**
 * 
 * @author Ahmed.Dakrory
 *
 */


@NamedQueries({
	
	
	@NamedQuery(name="expenses.getAll",
		     query="SELECT c FROM expenses c"
		     )
	,
	@NamedQuery(name="expenses.getById",
	query = "from expenses d where d.id = :id"
			)
	
	
	
})

@Entity
@Table(name = "expenses")
public class expenses {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "price")
	private Integer price;
	
	@Column(name = "quantity")
	private Integer quantity;

	@ManyToOne
	@JoinColumn(name = "addedByUser_id")
	private user addedByUser_id;
	
	@Column(name = "statues")
	private Integer statues;
	
	@Column(name = "date")
	private Calendar date;
	
	
	@ManyToOne
	@JoinColumn(name = "boughtByUser_id")
	private user boughtByUser_id;


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


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Integer getPrice() {
		return price;
	}


	public void setPrice(Integer price) {
		this.price = price;
	}


	public user getAddedByUser_id() {
		return addedByUser_id;
	}


	public void setAddedByUser_id(user addedByUser_id) {
		this.addedByUser_id = addedByUser_id;
	}


	public Integer getStatues() {
		return statues;
	}


	public void setStatues(Integer statues) {
		this.statues = statues;
	}


	public Calendar getDate() {
		return date;
	}


	public void setDate(Calendar date) {
		this.date = date;
	}


	public user getBoughtByUser_id() {
		return boughtByUser_id;
	}


	public void setBoughtByUser_id(user boughtByUser_id) {
		this.boughtByUser_id = boughtByUser_id;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	
	
	

}
