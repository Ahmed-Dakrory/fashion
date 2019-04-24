package main.com.crm.productBean;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import main.com.crm.product.product;
import main.com.crm.product.productAppServiceImpl;
import main.com.crm.loginNeeds.user;


@ManagedBean(name = "productsBean")
@SessionScoped
public class productsBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 655527256791420301L;

	private List<product> listOfProducts;
	

	@ManagedProperty(value = "#{productFacadeImpl}")
	private productAppServiceImpl productDataFacede; 
	
	 
	@ManagedProperty(value = "#{loginBean}")
	private main.com.crm.loginNeeds.loginBean loginBean;
	
	
	private product selectedProducts;
	
	
	

	private product addedProducts;
	
	private String dateString;
	
	
	
	
	@PostConstruct
	public void init() {
		addedProducts=new product();
		addedProducts.setAddedByUser_id(new user());
		
		refresh();
		
	}
	
	public void refresh(){
		listOfProducts=productDataFacede.getAll();
	}

	
	public void selectProducts(int productId) {
		selectedProducts=productDataFacede.getById(productId);
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/products/productDetails.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	 
	     
	    
	     
	
	public void goToAddNewProducts() {
		
		addedProducts=new product();
		addedProducts.setAddedByUser_id(new user());
		dateString=null;
		
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/products/addProduct.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addNewProducts() {
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-dd-MM HH:mm:ss"); 
		try {
			if(dateString!=null) {
			Date date=formatter.parse(dateString);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			addedProducts.setDate(cal);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		addedProducts.setAddedByUser_id(loginBean.getTheUserOfThisAccount());
		productDataFacede.addproduct(addedProducts);
		
		PrimeFaces.current().executeScript("new PNotify({\r\n" + 
				"			title: 'Success',\r\n" + 
				"			text: 'New product has been added.',\r\n" + 
				"			type: 'success'\r\n" + 
				"		});");
		
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/products/product.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<product> getListOfProducts() {
		return listOfProducts;
	}

	public void setListOfProducts(List<product> listOfProducts) {
		this.listOfProducts = listOfProducts;
	}

	public productAppServiceImpl getProductDataFacede() {
		return productDataFacede;
	}

	public void setProductDataFacede(productAppServiceImpl productDataFacede) {
		this.productDataFacede = productDataFacede;
	}

	public main.com.crm.loginNeeds.loginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(main.com.crm.loginNeeds.loginBean loginBean) {
		this.loginBean = loginBean;
	}

	public product getSelectedProducts() {
		return selectedProducts;
	}

	public void setSelectedProducts(product selectedProducts) {
		this.selectedProducts = selectedProducts;
	}

	public product getAddedProducts() {
		return addedProducts;
	}

	public void setAddedProducts(product addedProducts) {
		this.addedProducts = addedProducts;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
	
}
