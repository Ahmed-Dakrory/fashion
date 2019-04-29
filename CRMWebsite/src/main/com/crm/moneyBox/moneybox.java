package main.com.crm.moneyBox;

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
	
	
	@NamedQuery(name="moneybox.getAll",
		     query="SELECT c FROM moneybox c"
		     )
	,
	@NamedQuery(name="moneybox.getById",
	query = "from moneybox d where d.id = :id"
			)
	,
	@NamedQuery(name="moneybox.getByUserId",
	query = "from moneybox d where d.user_id.id = :id"
			)
	
	
	
})

@Entity
@Table(name = "moneybox")
public class moneybox {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "totalMoney")
	private Float totalMoney;
	
	@Column(name = "moneyRemains")
	private Float moneyRemains;
	
	@Column(name = "payable")
	private Float payable;
	
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private user user_id;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	


	public Float getTotalMoney() {
		return totalMoney;
	}


	public void setTotalMoney(Float totalMoney) {
		this.totalMoney = totalMoney;
	}


	public Float getMoneyRemains() {
		return moneyRemains;
	}


	public void setMoneyRemains(Float moneyRemains) {
		this.moneyRemains = moneyRemains;
	}


	public Float getPayable() {
		return payable;
	}


	public void setPayable(Float payable) {
		this.payable = payable;
	}


	public user getUser_id() {
		return user_id;
	}


	public void setUser_id(user user_id) {
		this.user_id = user_id;
	}


	
	
	
	
	

}
