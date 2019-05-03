package main.com.crm.loginNeeds;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;


/**
 * 
 * @author Ahmed.Dakrory
 *
 */


@NamedQueries({
	
	
	@NamedQuery(name="user.getAll",
		     query="SELECT c FROM user c"
		     )
	,
	@NamedQuery(name="user.getById",
	query = "from user d where d.id = :id"
			)
	
	,
	@NamedQuery(name="user.getByEmail",
	query = "from user d where d.email = :email"
			)
	
	,
	@NamedQuery(name="user.getAllWithRole",
	query = "from user d where d.role = :role"
			)
	
	,
	@NamedQuery(name="user.getByMailAndPassword",
	query = "from user d where d.email = :email and d.password = :password and active = :active"
			)
	
})

@Entity
@Table(name = "user")
public class user {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;
	
	
	@Column(name = "email")
	private String email;
	

	@Column(name = "password")
	private String password;
	

	@Column(name = "phone")
	private String phone;
	
	@Column(name = "address")
	private String address;

	public static int ROLE_ADMIN=0;
	public static int ROLE_SHAREHOLDER=1;
	public static int ROLE_CUSTOMER=2;

	@Column(name = "role")
	private Integer role;
	
	
	@Column(name="image")
	@Lob
	private byte[] image;


	@Column(name = "active")
	private Integer active;
	
	
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




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public String getPassword() {
		return password;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	public String getPhone() {
		return phone;
	}




	public void setPhone(String phone) {
		this.phone = phone;
	}


	


	public String getAddress() {
		return address;
	}




	public void setAddress(String address) {
		this.address = address;
	}




	public Integer getRole() {
		return role;
	}




	public void setRole(Integer role) {
		this.role = role;
	}




	public byte[] getImage() {
		return image;
	}




	public void setImage(byte[] image) {
		this.image = image;
	}




	public Integer getActive() {
		return active;
	}




	public void setActive(Integer active) {
		this.active = active;
	}


	public String getRoleString() {
		if(role==0) {
			return "Admin";
		}else if(role==1) {
			return "Shareholder";
		}else {
			return "Customer";
		}
	}

	public String getphoto() {
		if(getImage()!=null) {
		String imageString= new String(Base64.encodeBase64(image));

		return "data:image/png;base64, "+imageString;
		}else {
			return "/resources/images/comment-img3.jpg";
		}
	}

}
