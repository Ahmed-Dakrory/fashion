package main.com.crm.asset;


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

import main.com.crm.expense.expenses;


/**
 * 
 * @author Ahmed.Dakrory
 *
 */


@NamedQueries({
	
	
	@NamedQuery(name="asset.getAll",
		     query="SELECT c FROM asset c"
		     )
	,
	@NamedQuery(name="asset.getById",
	query = "from asset d where d.id = :id"
			)
	
	
})

@Entity
@Table(name = "asset")
public class asset {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;


	@Column(name = "quantity")
	private Integer quantity;

	
	@Column(name = "boughtFrom")
	private Integer boughtFrom;
	
	@Column(name = "storedIn")
	private Integer storedIn;
		

	@ManyToOne
	@JoinColumn(name = "expenses_id")
	private expenses expenses_id;

	
	@Column(name="image")
	@Lob
	private byte[] image;
	
	@Column(name="attachedInvoice")
	@Lob
	private byte[] attachedInvoice;





	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public Integer getBoughtFrom() {
		return boughtFrom;
	}


	public void setBoughtFrom(Integer boughtFrom) {
		this.boughtFrom = boughtFrom;
	}


	public Integer getStoredIn() {
		return storedIn;
	}


	public void setStoredIn(Integer storedIn) {
		this.storedIn = storedIn;
	}


	

	public expenses getExpenses_id() {
		return expenses_id;
	}


	public void setExpenses_id(expenses expenses_id) {
		this.expenses_id = expenses_id;
	}


	public byte[] getImage() {
		return image;
	}


	public void setImage(byte[] image) {
		this.image = image;
	}


	public byte[] getAttachedInvoice() {
		return attachedInvoice;
	}


	public void setAttachedInvoice(byte[] attachedInvoice) {
		this.attachedInvoice = attachedInvoice;
	}


	public String getStateForInvoice(int i) {
		if(i==0) {
			if(attachedInvoice==null) {
				return "none"; 
			}else {
				return "inherite";
			}
		}else {
			if(image==null) {
				return "inherite";
			}else {
				return "none";
			}
		}
	}


	public String getinvoice() {
		if(attachedInvoice==null) {
		String imageString= new String(Base64.encodeBase64(attachedInvoice));

		return "data:image/png;base64, "+imageString;
		}else {
			return "images/comment-img3.jpg";
		}
	}

	public String getStateForImage(int i) {
		if(i==0) {
			if(image==null) {
				return "none"; 
			}else {
				return "inherite";
			}
		}else {
			if(image==null) {
				return "inherite";
			}else {
				return "none";
			}
		}
	}


	public String getphoto() {
		if(image==null) {
		String imageString= new String(Base64.encodeBase64(image));

		return "data:image/png;base64, "+imageString;
		}else {
			return "images/comment-img2.jpg";
		}
	}

}
