package main.com.crm.assetBean;

import java.io.IOException;
import java.io.Serializable;
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
	 

	private asset selectedAsset;
	
	
	

	private expenses addedExpenses;
	private asset addedAsset;
	
	@ManagedProperty(value = "#{userFacadeImpl}")
	private userAppServiceImpl userDataFacede; 
	private List<user> allUsers;
	
	
	@PostConstruct
	public void init() {
		addedExpenses=new expenses();
		addedAsset=new asset();
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
	
	public void addNewAsset() {
		addedAsset.setExpenses_id(addedExpenses);
		assetDataFacede.addasset(addedAsset);
		PrimeFaces.current().executeScript("new PNotify({\r\n" + 
				"			title: 'Success',\r\n" + 
				"			text: 'New asset has been added.',\r\n" + 
				"			type: 'success'\r\n" + 
				"		});");
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
	
	
	
	
	
}
