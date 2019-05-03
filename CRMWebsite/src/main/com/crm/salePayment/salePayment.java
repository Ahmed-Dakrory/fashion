package main.com.crm.salePayment;

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

import main.com.crm.sale.sale;


/**
 * 
 * @author Ahmed.Dakrory
 *
 */


@NamedQueries({
	
	
	@NamedQuery(name="salePayment.getAll",
		     query="SELECT c FROM salePayment c"
		     )
	,
	@NamedQuery(name="salePayment.getById",
	query = "from salePayment d where d.id = :id"
			)
	
	,
	@NamedQuery(name="salePayment.getBySaleId",
	query = "from salePayment d where d.sale_id.id = :id"
			)
	,
	@NamedQuery(name="salePayment.getAllBetweenDateAndStatue",
	query = "from salePayment d where d.dateEnd > :dateLower and d.dateEnd < :dateHigher and d.payedOrNot = :statue"
			)
	
	
})

@Entity
@Table(name = "salePayment")
public class salePayment {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	
	@Column(name = "amount")
	private Float amount;
	
	
	public static int PAYED=1;
	public static int NOT_PAYED=2;
	
	@Column(name = "payedOrNot")
	private Integer payedOrNot;
	
	
	@ManyToOne
	@JoinColumn(name = "sale_id")
	private sale sale_id;
	
	
	@Column(name = "dateEnd")
	private Calendar dateEnd;


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


	public Float getAmount() {
		return amount;
	}


	public void setAmount(Float amount) {
		this.amount = amount;
	}


	public Integer getPayedOrNot() {
		return payedOrNot;
	}


	public void setPayedOrNot(Integer payedOrNot) {
		this.payedOrNot = payedOrNot;
	}


	public sale getSale_id() {
		return sale_id;
	}


	public void setSale_id(sale sale_id) {
		this.sale_id = sale_id;
	}


	public Calendar getDateEnd() {
		return dateEnd;
	}


	public void setDateEnd(Calendar dateEnd) {
		this.dateEnd = dateEnd;
	}
	
	
	public String getPayedOrNotString() {
		if(payedOrNot==1) {
			return "Payed";
		}else {
			return "Not payed";
		}
	}
	

}
