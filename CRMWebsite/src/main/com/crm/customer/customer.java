package main.com.crm.customer;

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
	
	
	@NamedQuery(name="customer.getAll",
		     query="SELECT c FROM customer c"
		     )
	,
	@NamedQuery(name="customer.getById",
	query = "from customer d where d.id = :id"
			)
	
	
	
})

@Entity
@Table(name = "customer")
public class customer {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "address")
	private String address;
	
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private user user_id;


	
	
	
	
	
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public user getUser_id() {
		return user_id;
	}


	public void setUser_id(user user_id) {
		this.user_id = user_id;
	}
	
	

}
