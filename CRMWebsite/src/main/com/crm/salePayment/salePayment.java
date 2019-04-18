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
	
	
	
})

@Entity
@Table(name = "salePayment")
public class salePayment {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	
	@Column(name = "amount")
	private Integer amount;
	
	@Column(name = "payedOrNot")
	private Integer payedOrNot;
	
	
	@ManyToOne
	@JoinColumn(name = "sale_id")
	private sale sale_id;
	
	
	@Column(name = "dateEnd")
	private Calendar dateEnd;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getAmount() {
		return amount;
	}


	public void setAmount(Integer amount) {
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
	
	
	
	

}
