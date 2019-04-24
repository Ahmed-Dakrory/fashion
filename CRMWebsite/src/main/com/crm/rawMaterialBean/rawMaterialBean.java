package main.com.crm.rawMaterialBean;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;

import main.com.crm.rawMaterial.rawMaterial;
import main.com.crm.rawMaterial.rawMaterialAppServiceImpl;
import main.com.crm.expense.expenses;
import main.com.crm.expense.expensesAppServiceImpl;
import main.com.crm.loginNeeds.user;
import main.com.crm.loginNeeds.userAppServiceImpl;


@ManagedBean(name = "rawMaterialBean")
@SessionScoped
public class rawMaterialBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 655527256791420301L;

	private List<rawMaterial> listOfrawMaterials;
	

	@ManagedProperty(value = "#{rawMaterialFacadeImpl}")
	private rawMaterialAppServiceImpl rawMaterialDataFacede; 
	
	@ManagedProperty(value = "#{expensesFacadeImpl}")
	private expensesAppServiceImpl expensesDataFacede; 
	 
	@ManagedProperty(value = "#{loginBean}")
	private main.com.crm.loginNeeds.loginBean loginBean;
	
	
	private rawMaterial selectedrawMaterial;
	
	
	

	private expenses addedExpenses;
	private rawMaterial addedrawMaterial;
	private String dateString;
	private int paymentAddingMethod;
	private int unit;
	
	@ManagedProperty(value = "#{userFacadeImpl}")
	private userAppServiceImpl userDataFacede; 
	private List<user> allUsers;
	
	
	@PostConstruct
	public void init() {
		addedExpenses=new expenses();
		addedrawMaterial=new rawMaterial();
		addedExpenses.setAddedByUser_id(new user());
		addedExpenses.setBoughtByUser_id(new user());
		addedrawMaterial.setExpenses_id(addedExpenses);
		paymentAddingMethod=1;
		unit=1;
		refresh();
		
	}
	
	public void refresh(){
		allUsers=userDataFacede.getAll();
		listOfrawMaterials=rawMaterialDataFacede.getAll();
	}

	
	public void selectrawMaterial(int rawMaterialId) {
		selectedrawMaterial=rawMaterialDataFacede.getById(rawMaterialId);
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/rawMaterial/rawMaterialDetails.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	 
	     
	    
	     
	public void handleImageUpload(FileUploadEvent event) {

		addedrawMaterial.setImage(event.getFile().getContents());
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	public void handleInvoiceUpload(FileUploadEvent event) {

		addedrawMaterial.setAttachedInvoice(event.getFile().getContents());
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	public void goToAddNewrawMaterial() {
		
		addedExpenses=new expenses();
		addedrawMaterial=new rawMaterial();
		addedExpenses.setAddedByUser_id(new user());
		addedExpenses.setBoughtByUser_id(new user());
		addedrawMaterial.setExpenses_id(addedExpenses);
		paymentAddingMethod=1;
		unit=1;
		dateString=null;
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/rawMaterial/addrawMaterial.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addNewrawMaterial() {
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-dd-MM HH:mm:ss"); 
		try {
			if(dateString!=null) {
			Date date=formatter.parse(dateString);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			addedExpenses.setDate(cal);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		addedExpenses.setStatues(1);
		addedExpenses.setAddedByUser_id(loginBean.getTheUserOfThisAccount());
		expensesDataFacede.addexpenses(addedExpenses);
		addedrawMaterial.setUnit(unit);
		addedrawMaterial.setExpenses_id(addedExpenses);
		rawMaterialDataFacede.addrawMaterial(addedrawMaterial);
		PrimeFaces.current().executeScript("new PNotify({\r\n" + 
				"			title: 'Success',\r\n" + 
				"			text: 'New rawMaterial has been added.',\r\n" + 
				"			type: 'success'\r\n" + 
				"		});");
		
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/rawMaterial/rawMaterials.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	public List<rawMaterial> getListOfrawMaterials() {
		return listOfrawMaterials;
	}

	public void setListOfrawMaterials(List<rawMaterial> listOfrawMaterials) {
		this.listOfrawMaterials = listOfrawMaterials;
	}

	

	public rawMaterialAppServiceImpl getRawMaterialDataFacede() {
		return rawMaterialDataFacede;
	}

	public void setRawMaterialDataFacede(rawMaterialAppServiceImpl rawMaterialDataFacede) {
		this.rawMaterialDataFacede = rawMaterialDataFacede;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public rawMaterial getSelectedrawMaterial() {
		return selectedrawMaterial;
	}

	public void setSelectedrawMaterial(rawMaterial selectedrawMaterial) {
		this.selectedrawMaterial = selectedrawMaterial;
	}

	public rawMaterial getAddedrawMaterial() {
		return addedrawMaterial;
	}

	public void setAddedrawMaterial(rawMaterial addedrawMaterial) {
		this.addedrawMaterial = addedrawMaterial;
	}

	public userAppServiceImpl getUserDataFacede() {
		return userDataFacede;
	}

	public void setUserDataFacede(userAppServiceImpl userDataFacede) {
		this.userDataFacede = userDataFacede;
	}

	public List<user> getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(List<user> allUsers) {
		this.allUsers = allUsers;
	}

	public expenses getAddedExpenses() {
		return addedExpenses;
	}

	public void setAddedExpenses(expenses addedExpenses) {
		this.addedExpenses = addedExpenses;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public int getPaymentAddingMethod() {
		return paymentAddingMethod;
	}

	public void setPaymentAddingMethod(int paymentAddingMethod) {
		this.paymentAddingMethod = paymentAddingMethod;
	}

	public expensesAppServiceImpl getExpensesDataFacede() {
		return expensesDataFacede;
	}

	public void setExpensesDataFacede(expensesAppServiceImpl expensesDataFacede) {
		this.expensesDataFacede = expensesDataFacede;
	}

	public main.com.crm.loginNeeds.loginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(main.com.crm.loginNeeds.loginBean loginBean) {
		this.loginBean = loginBean;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}
	
	
	
	
	
}
