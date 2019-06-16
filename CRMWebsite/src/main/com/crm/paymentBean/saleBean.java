package main.com.crm.paymentBean;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import main.com.crm.loginNeeds.user;
import main.com.crm.loginNeeds.userAppServiceImpl;
import main.com.crm.product.product;
import main.com.crm.product.productAppServiceImpl;
import main.com.crm.productitem.productitem;
import main.com.crm.productitem.productitemAppServiceImpl;
import main.com.crm.sale.sale;
import main.com.crm.sale.saleAppServiceImpl;
import main.com.crm.salePayment.salePayment;
import main.com.crm.salePayment.salePaymentAppServiceImpl;


@ManagedBean(name = "saleBean")
@SessionScoped
public class saleBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 655527256791420301L;

	private List<sale> listOfSales;
	

	@ManagedProperty(value = "#{saleFacadeImpl}")
	private saleAppServiceImpl saleDataFacede;
	
	@ManagedProperty(value = "#{salePaymentFacadeImpl}")
	private salePaymentAppServiceImpl salePaymentDataFacede;
	
	@ManagedProperty(value = "#{userFacadeImpl}")
	private userAppServiceImpl userDataFacede;
	
	@ManagedProperty(value = "#{productFacadeImpl}")
	private productAppServiceImpl productDataFacede;
	
	

	@ManagedProperty(value = "#{productitemFacadeImpl}")
	private productitemAppServiceImpl productitemDataFacede; 
	
	
	@ManagedProperty(value = "#{loginBean}")
	private main.com.crm.loginNeeds.loginBean loginBean;
	
	
	private sale selectedSale;
	private List<salePayment> listOfSeletedSalePayments;
	
	private List<user> allCustomers;
	private List<product> allproducts;
	

	private sale addedSale;
	private List<salePayment> listOfAddedSalePayments;
	
	private String dateStringStart;
	private String dateStringEnd;
	/*
	 * cash 1
	 * ration 2
	 */
	private int paymentAddingMethod;
	private int numberOfInstallments;
	private Float deposite;
	
	private Calendar calStart ;
	private Calendar calEnd;
	private Calendar calNow;
	
	private salePayment selectedSalePaymentForInvoice;
	
	@PostConstruct
	public void init() {
		addedSale=new sale();
		listOfAddedSalePayments=new ArrayList<salePayment>();
		addedSale.setAddedByUser_id(new user());
		addedSale.setCustomer_id(new user());
		addedSale.setProduct_id(new product());
		
		paymentAddingMethod=1;
		numberOfInstallments=1;
		dateStringStart="";
		dateStringEnd="";
		refresh();
		
	}
	
	public void refresh(){
		calNow=Calendar.getInstance();
		listOfSales=saleDataFacede.getAll();
		allCustomers=userDataFacede.getAllWithRole(user.ROLE_CUSTOMER);
		List<user> allPlacesCustomers=userDataFacede.getAllWithRole(user.ROLE_PLACE);
		if(allPlacesCustomers!=null &&allPlacesCustomers.size()>0) {
			allCustomers.addAll(allPlacesCustomers);
		}
		allproducts=productDataFacede.getAll();
		
		addedSale=new sale();
		listOfAddedSalePayments=new ArrayList<salePayment>();
		addedSale.setAddedByUser_id(new user());
		addedSale.setCustomer_id(new user());
		addedSale.setProduct_id(new product());
		
		paymentAddingMethod=1;
		numberOfInstallments=1;
		dateStringStart="";
		dateStringEnd="";
	}

	public void setPaymentState(int salePaymentId,int state) {
		salePayment s=salePaymentDataFacede.getById(salePaymentId);
		s.setPayedOrNot(state);
		salePaymentDataFacede.addsalePayment(s);
		listOfSeletedSalePayments=salePaymentDataFacede.getBySaleId(s.getSale_id().getId());
	}
	
	public void selectPayments(int saleId) {
		selectedSale=saleDataFacede.getById(saleId);
		listOfSeletedSalePayments=salePaymentDataFacede.getBySaleId(saleId);
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/fashion/pages/secured/admin/payment/paymentDetails.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void selectSalaPayment(int salePaymentId) {
		selectedSalePaymentForInvoice=salePaymentDataFacede.getById(salePaymentId);
		
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/fashion/pages/secured/admin/payment/salePaymentDetails.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
	public void delete(int paymentId) {
		sale deletedSale=saleDataFacede.getById(paymentId);
		
		
		//Release the product from the product items table
		product productReturnQTY=productDataFacede.getById(deletedSale.getProduct_id().getId());
		
		productReturnQTY.setQuantityAvailable(productReturnQTY.getQuantityAvailable()+deletedSale.getQuantity());
		productDataFacede.addproduct(productReturnQTY);
		
		//Release the product from the product items table
		List<productitem> pItems=productitemDataFacede.getitemsWithSaleId(deletedSale.getId());
		for(int i=0;i<pItems.size();i++) {
			pItems.get(i).setCustomer_id(null);
			pItems.get(i).setState(productitem.STATE_PRODUCED);
			pItems.get(i).setRecievedPriceForThisItem((float) 0);
			pItems.get(i).setSale_id(null);
			productitemDataFacede.addproductitem(pItems.get(i));
		}
		
		List<salePayment> allDeletedDetails=salePaymentDataFacede.getBySaleId(paymentId);
		for(int i=0;i<allDeletedDetails.size();i++) {
			salePaymentDataFacede.delete(allDeletedDetails.get(i));
		}
		saleDataFacede.delete(deletedSale);
	}
	    
	     
	
	public void goToAddNewSale() {
		
		addedSale=new sale();
		listOfAddedSalePayments=new ArrayList<salePayment>();
		addedSale.setAddedByUser_id(new user());
		addedSale.setCustomer_id(new user());
		addedSale.setProduct_id(new product());
		
		paymentAddingMethod=1;
		numberOfInstallments=1;
		dateStringStart="";
		dateStringEnd="";
		
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/fashion/pages/secured/admin/payment/addPayment.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addNewSale() {
		product p=productDataFacede.getById(addedSale.getProduct_id().getId());
		if(addedSale.getQuantity()>p.getQuantityAvailable()) {
			PrimeFaces.current().executeScript("new PNotify({\r\n" + 
					"			title: 'Problem!',\r\n" + 
					"			text: 'N. of products you want is less than the available!',\r\n" + 
					"			type: 'error',\r\n" + 
					"			left:\"1%\"\r\n" + 
					"		});");
		}else {
		//Adjust Date start and end
		addDateStartAndEnd();
		addedSale.setType(paymentAddingMethod);
		addedSale.setDate(calStart);
		addedSale.setAddedByUser_id(loginBean.getTheUserOfThisAccount());
		saleDataFacede.addsale(addedSale);
		adjustSalePaymentInstallments();
		adjustItemsForThisProduct();
		addInstallments();
		
		
		p.setQuantityAvailable(p.getQuantityAvailable()-addedSale.getQuantity());
		productDataFacede.addproduct(p);
		
		
		PrimeFaces.current().executeScript("new PNotify({\r\n" + 
				"			title: 'Success',\r\n" + 
				"			text: 'New payment has been added.',\r\n" + 
				"			type: 'success'\r\n" + 
				"		});");
		
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/fashion/pages/secured/admin/payment/payments.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}

	private void adjustItemsForThisProduct() {
		// TODO Auto-generated method stub
		List<productitem> addedProductItems=productitemDataFacede.getAvailableItemsWithQuantity(addedSale.getProduct_id().getId(), addedSale.getQuantity());
		for(int i=0;i<addedProductItems.size();i++) {
			productitem pItem=addedProductItems.get(i);
			pItem.setState(productitem.STATE_PAYED);
			pItem.setRecievedPriceForThisItem(addedSale.getPriceTotal()/addedSale.getQuantity());
			pItem.setCustomer_id(addedSale.getCustomer_id());
			pItem.setSale_id(addedSale);
			productitemDataFacede.addproductitem(pItem);
		}
	}

	private void addInstallments() {
		for(int i=0;i<listOfAddedSalePayments.size();i++) {
			salePaymentDataFacede.addsalePayment(listOfAddedSalePayments.get(i));
		}
	}

	public void print() {
		System.out.println("Ahmed Dakrory: "+addedSale.getPriceTotal());
	}
	

	private void adjustSalePaymentInstallments() {
		long timeStart=calStart.getTimeInMillis();
		long timeEnd=calEnd.getTimeInMillis();
		long periodBetweenEachInstallment=(timeEnd-timeStart)/numberOfInstallments;
		
		salePayment firstDeposite=new salePayment();
		firstDeposite.setPayedOrNot(1);
		firstDeposite.setDateEnd(calStart);
		firstDeposite.setSale_id(addedSale);
		if(paymentAddingMethod==1) {
			firstDeposite.setAmount(addedSale.getPriceTotal());
			listOfAddedSalePayments.add(firstDeposite);
		}else {
			firstDeposite.setAmount(deposite);
			listOfAddedSalePayments.add(firstDeposite);
			Float valueOfInstallment=(addedSale.getPriceTotal()-deposite)/numberOfInstallments;
			for(int i=1;i<=numberOfInstallments;i++) {
				long timeForPay=timeStart+periodBetweenEachInstallment*i;
				Calendar c=Calendar.getInstance();
				c.setTimeInMillis(timeForPay);
				
				salePayment s=new salePayment();
				s.setPayedOrNot(2);
				s.setDateEnd(c);
				s.setSale_id(addedSale);
				s.setAmount(valueOfInstallment);
				listOfAddedSalePayments.add(s);
				
			}
		}
		
		
		
	}

	private void addDateStartAndEnd() {

		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-dd-MM HH:mm:ss"); 
		calStart = Calendar.getInstance();
		calEnd = Calendar.getInstance();
		try {
			if(!dateStringStart.equals("")) {
				Date date=formatter.parse(dateStringStart);
				calStart.setTime(date);
			}
			
			
			if(!dateStringEnd.equals("")) {
				Date date=formatter.parse(dateStringEnd);
				calEnd.setTime(date);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
	}

	
	
	
	public List<sale> getListOfSales() {
		return listOfSales;
	}

	public void setListOfSales(List<sale> listOfSales) {
		this.listOfSales = listOfSales;
	}

	public saleAppServiceImpl getSaleDataFacede() {
		return saleDataFacede;
	}

	public void setSaleDataFacede(saleAppServiceImpl saleDataFacede) {
		this.saleDataFacede = saleDataFacede;
	}

	public salePaymentAppServiceImpl getSalePaymentDataFacede() {
		return salePaymentDataFacede;
	}

	public void setSalePaymentDataFacede(salePaymentAppServiceImpl salePaymentDataFacede) {
		this.salePaymentDataFacede = salePaymentDataFacede;
	}

	public userAppServiceImpl getUserDataFacede() {
		return userDataFacede;
	}

	public void setUserDataFacede(userAppServiceImpl userDataFacede) {
		this.userDataFacede = userDataFacede;
	}

	public main.com.crm.loginNeeds.loginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(main.com.crm.loginNeeds.loginBean loginBean) {
		this.loginBean = loginBean;
	}

	public sale getSelectedSale() {
		return selectedSale;
	}

	public void setSelectedSale(sale selectedSale) {
		this.selectedSale = selectedSale;
	}

	public List<salePayment> getListOfSeletedSalePayments() {
		return listOfSeletedSalePayments;
	}

	public void setListOfSeletedSalePayments(List<salePayment> listOfSeletedSalePayments) {
		this.listOfSeletedSalePayments = listOfSeletedSalePayments;
	}

	public List<user> getAllCustomers() {
		return allCustomers;
	}

	public void setAllCustomers(List<user> allCustomers) {
		this.allCustomers = allCustomers;
	}

	public sale getAddedSale() {
		return addedSale;
	}

	public void setAddedSale(sale addedSale) {
		this.addedSale = addedSale;
	}

	public List<salePayment> getListOfAddedSalePayments() {
		return listOfAddedSalePayments;
	}

	public void setListOfAddedSalePayments(List<salePayment> listOfAddedSalePayments) {
		this.listOfAddedSalePayments = listOfAddedSalePayments;
	}

	public String getDateStringStart() {
		return dateStringStart;
	}

	public void setDateStringStart(String dateStringStart) {
		this.dateStringStart = dateStringStart;
	}

	public String getDateStringEnd() {
		return dateStringEnd;
	}

	public void setDateStringEnd(String dateStringEnd) {
		this.dateStringEnd = dateStringEnd;
	}

	public int getPaymentAddingMethod() {
		return paymentAddingMethod;
	}

	public void setPaymentAddingMethod(int paymentAddingMethod) {
		this.paymentAddingMethod = paymentAddingMethod;
	}

	public int getNumberOfInstallments() {
		return numberOfInstallments;
	}

	public void setNumberOfInstallments(int numberOfInstallments) {
		this.numberOfInstallments = numberOfInstallments;
	}

	public Float getDeposite() {
		return deposite;
	}

	public void setDeposite(Float deposite) {
		this.deposite = deposite;
	}

	public Calendar getCalStart() {
		return calStart;
	}

	public void setCalStart(Calendar calStart) {
		this.calStart = calStart;
	}

	public Calendar getCalEnd() {
		return calEnd;
	}

	public void setCalEnd(Calendar calEnd) {
		this.calEnd = calEnd;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public productAppServiceImpl getProductDataFacede() {
		return productDataFacede;
	}

	public void setProductDataFacede(productAppServiceImpl productDataFacede) {
		this.productDataFacede = productDataFacede;
	}

	public List<product> getAllproducts() {
		return allproducts;
	}

	public void setAllproducts(List<product> allproducts) {
		this.allproducts = allproducts;
	}

	public Calendar getCalNow() {
		return calNow;
	}

	public void setCalNow(Calendar calNow) {
		this.calNow = calNow;
	}

	public productitemAppServiceImpl getProductitemDataFacede() {
		return productitemDataFacede;
	}

	public void setProductitemDataFacede(productitemAppServiceImpl productitemDataFacede) {
		this.productitemDataFacede = productitemDataFacede;
	}

	public salePayment getSelectedSalePaymentForInvoice() {
		return selectedSalePaymentForInvoice;
	}

	public void setSelectedSalePaymentForInvoice(salePayment selectedSalePaymentForInvoice) {
		this.selectedSalePaymentForInvoice = selectedSalePaymentForInvoice;
	}

	
	
	
	
	
	
}
