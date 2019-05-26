package main.com.crm.productitem;


import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import main.com.crm.loginNeeds.user;
import main.com.crm.product.product;
import main.com.crm.sale.sale;
import net.glxn.qrgen.core.vcard.VCard;
import net.glxn.qrgen.javase.QRCode;


/**
 * 
 * @author Ahmed.Dakrory
 *
 */


@NamedQueries({
	
	
	@NamedQuery(name="productitem.getAll",
		     query="SELECT c FROM productitem c"
		     )
	,
	@NamedQuery(name="productitem.getById",
	query = "from productitem d where d.id = :id"
			)
	
	,
	@NamedQuery(name="productitem.getByProductId",
	query = "from productitem d where d.product_id = :product_id"
			)
	
	,
	@NamedQuery(name="productitem.getBySaleId",
	query = "from productitem d where d.sale_id = :sale_id"
			)
	
	,
	@NamedQuery(name="productitem.getProductsAvailableItems",
	query = "from productitem d where d.product_id = :product_id and d.state = 0 or d.state=2"
			)
	
	,
	@NamedQuery(name="productitem.getLastNProducts",
	query = "from productitem d order by d.date desc"
			)
	
})

@Entity
@Table(name = "productitem")
public class productitem {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	
	@Column(name = "state")
	private Integer state;
	
	
	@Column(name = "recievedPriceForThisItem")
	private Float recievedPriceForThisItem;
	

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private user customer_id;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private product product_id;
	
	@ManyToOne
	@JoinColumn(name = "sale_id")
	private sale sale_id;
	
	
	@Column(name = "date")
	private Calendar date;
	
	@Column(name = "lastUpdate")
	private Calendar lastUpdate;
	

	

	public static int STATE_PRODUCED=0;
	public static int STATE_PAYED=1;
	public static int STATE_RETURN=2;
	public static int STATE_DAMAGE=3;
	
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}


	public product getProduct_id() {
		return product_id;
	}

	public void setProduct_id(product product_id) {
		this.product_id = product_id;
	}


	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Calendar getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Calendar lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Float getRecievedPriceForThisItem() {
		return recievedPriceForThisItem;
	}

	public void setRecievedPriceForThisItem(Float recievedPriceForThisItem) {
		this.recievedPriceForThisItem = recievedPriceForThisItem;
	}

	public user getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(user customer_id) {
		this.customer_id = customer_id;
	}

	public sale getSale_id() {
		return sale_id;
	}

	public void setSale_id(sale sale_id) {
		this.sale_id = sale_id;
	}

	public String getStateString() {
		if(state==STATE_PRODUCED) {
			return "Produced";
		}else if(state==STATE_PAYED) {
			return "Payed";
		}else if(state==STATE_RETURN) {
			return "Returns";
		}else {
			return "Damaged";
		}
	}
	


	public String getQr() {
		
		VCard vcard=new VCard(product_id.getName())
				
				
				//Address == time in millis
				.setAddress(String.valueOf((date.getTimeInMillis())))
				
				//Email == id
				.setEmail(String.valueOf(id))
				
				//Phone Number == productId
				.setPhoneNumber(String.valueOf(product_id.getId()));
		
		
		ByteArrayOutputStream out = QRCode.from(vcard).stream();
		
	
			String imageString= new String(Base64.encodeBase64(out.toByteArray()));
			
		

		return "data:image/png;base64, "+imageString;
		
	}

}
