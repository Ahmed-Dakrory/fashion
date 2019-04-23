package main.com.crm.assetBean;

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

import main.com.crm.asset.asset;
import main.com.crm.asset.assetAppServiceImpl;
import main.com.crm.expense.expenses;
import main.com.crm.expense.expensesAppServiceImpl;
import main.com.crm.loginNeeds.user;
import main.com.crm.loginNeeds.userAppServiceImpl;


@ManagedBean(name = "assetsBean")
@SessionScoped
public class assetsBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 655527256791420301L;

	private List<asset> listOfAssets;
	

	@ManagedProperty(value = "#{assetFacadeImpl}")
	private assetAppServiceImpl assetDataFacede; 
	
	@ManagedProperty(value = "#{expensesFacadeImpl}")
	private expensesAppServiceImpl expensesDataFacede; 
	 
	@ManagedProperty(value = "#{loginBean}")
	private main.com.crm.loginNeeds.loginBean loginBean;
	
	
	private asset selectedAsset;
	
	
	

	private expenses addedExpenses;
	private asset addedAsset;
	private String dateString;
	private int paymentAddingMethod;
	
	@ManagedProperty(value = "#{userFacadeImpl}")
	private userAppServiceImpl userDataFacede; 
	private List<user> allUsers;
	
	
	@PostConstruct
	public void init() {
		addedExpenses=new expenses();
		addedAsset=new asset();
		addedExpenses.setAddedByUser_id(new user());
		addedExpenses.setBoughtByUser_id(new user());
		paymentAddingMethod=1;
		refresh();
		
	}
	
	public void refresh(){
		allUsers=userDataFacede.getAll();
		listOfAssets=assetDataFacede.getAll();
	}

	
	public void selectAsset(int assetId) {
		selectedAsset=assetDataFacede.getById(assetId);
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/assets/assetDetails.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	 
	     
	    
	     
	public void handleImageUpload(FileUploadEvent event) {

		addedAsset.setImage(event.getFile().getContents());
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	public void handleInvoiceUpload(FileUploadEvent event) {

		addedAsset.setAttachedInvoice(event.getFile().getContents());
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	public void goToAddNewAsset() {
		
		addedExpenses=new expenses();
		addedAsset=new asset();
		addedExpenses.setAddedByUser_id(new user());
		addedExpenses.setBoughtByUser_id(new user());
		paymentAddingMethod=1;
		
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/assets/addAsset.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addNewAsset() {
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
		addedAsset.setExpenses_id(addedExpenses);
		assetDataFacede.addasset(addedAsset);
		PrimeFaces.current().executeScript("new PNotify({\r\n" + 
				"			title: 'Success',\r\n" + 
				"			text: 'New asset has been added.',\r\n" + 
				"			type: 'success'\r\n" + 
				"		});");
		
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/assets/assets.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	public List<asset> getListOfAssets() {
		return listOfAssets;
	}

	public void setListOfAssets(List<asset> listOfAssets) {
		this.listOfAssets = listOfAssets;
	}

	public assetAppServiceImpl getAssetDataFacede() {
		return assetDataFacede;
	}

	public void setAssetDataFacede(assetAppServiceImpl assetDataFacede) {
		this.assetDataFacede = assetDataFacede;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public asset getSelectedAsset() {
		return selectedAsset;
	}

	public void setSelectedAsset(asset selectedAsset) {
		this.selectedAsset = selectedAsset;
	}

	public asset getAddedAsset() {
		return addedAsset;
	}

	public void setAddedAsset(asset addedAsset) {
		this.addedAsset = addedAsset;
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
	
	
	
	
	
}
