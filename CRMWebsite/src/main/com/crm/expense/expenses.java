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
	
	,
	@NamedQuery(name="expenses.getAllExceptType",
	query = "from expenses d where d.type != :type"
			)
	,
	@NamedQuery(name="expenses.getAllForTypeBetweenDateAndRole",
	query = "from expenses d where d.date > :dateLower and d.date < :dateHigher and d.type = :type "
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
	
	@Column(name = "pricePerUnit")
	private Float pricePerUnit;
	
	@Column(name = "quantity")
	private Integer quantity;

	@ManyToOne
	@JoinColumn(name = "addedByUser_id")
	private user addedByUser_id;
	

	public static int PAYED=1;
	public static int REGARD_AS_SHARE=2;
	/*
	 * pay or addTo shares
	 */
	@Column(name = "payedOrAddToShares")
	private Integer payedOrAddToShares;
	
	
	/*
	 * pay or regard as payable(not payed)
	 */

	public static int STATUE_PAYED=1;
	public static int STATUE_REGARD_AS_PAyABLE=2;
	
	@Column(name = "statues")
	private Integer statues;
	
	
	/*
	 * 0 for normal expenses
	 * 1 for assets
	 * 2 for raw material
	 * 
	 */
	

	public static int TYPE_NORMAL_EXPENSES=0;
	public static int TYPE_ASSETS=1;
	public static int TYPE_RAW_MATERIAL=2;
	
	@Column(name = "type")
	private Integer type;
	
	@Column(name = "date")
	private Calendar date;
	
	
	@ManyToOne
	@JoinColumn(name = "boughtByUser_id")
	private user boughtByUser_id;


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


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	

	public Float getPricePerUnit() {
		return pricePerUnit;
	}


	public void setPricePerUnit(Float pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
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


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public Integer getPayedOrAddToShares() {
		return payedOrAddToShares;
	}


	public void setPayedOrAddToShares(Integer payedOrAddToShares) {
		this.payedOrAddToShares = payedOrAddToShares;
	}


	public String getTypeString() {
		if(type==expenses.TYPE_ASSETS) {
			return "Assets";
		}else if(type==expenses.TYPE_NORMAL_EXPENSES) {
			return "Normal Expenses";
		}else {
			return "Raw Material";
		}
	}
	
	public String getStatuesString() {
		if(statues==1) {
			return "payed";
		}else {
			return "payable";
		}
	}
	

}
