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
import main.com.crm.moneyBox.moneybox;
import main.com.crm.moneyBox.moneyboxAppServiceImpl;


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
	

	@ManagedProperty(value = "#{moneyboxFacadeImpl}")
	private moneyboxAppServiceImpl moneyboxDataFacede; 
	
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
		addedAsset.setExpenses_id(addedExpenses);
		paymentAddingMethod=1;
		refresh();
		
	}
	
	public void refresh(){
		allUsers=userDataFacede.getAllWithRole(user.ROLE_SHAREHOLDER);
		listOfAssets=assetDataFacede.getAll();
	}

	
	public void selectAsset(int assetId) {
		selectedAsset=assetDataFacede.getById(assetId);
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/assets/assetDetails.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void deleteAsset(int idAsset) {
		 asset aSt=assetDataFacede.getById(idAsset);
		 
		 try {
			assetDataFacede.delete(aSt);
			expensesDataFacede.delete(aSt.getExpenses_id());
			
			moneybox mB=moneyboxDataFacede.getByUserId(aSt.getExpenses_id().getBoughtByUser_id().getId());
			if(aSt.getExpenses_id().getStatues()==1) {
				// he pay this amount so we need retrieve it
				if(aSt.getExpenses_id().getPayedOrAddToShares()==1) {
					//Payed directly
					mB.setMoneyRemains(mB.getMoneyRemains()+(aSt.getExpenses_id().getPricePerUnit()*aSt.getExpenses_id().getQuantity()));
					
				}else {
					//Payed as shares
					mB.setTotalMoney(mB.getTotalMoney()-(aSt.getExpenses_id().getPricePerUnit()*aSt.getExpenses_id().getQuantity()));
					
				}
			}else {
				// pay as payable
				mB.setTotalMoney(mB.getTotalMoney()-(aSt.getExpenses_id().getPricePerUnit()*aSt.getExpenses_id().getQuantity()));
				mB.setPayable(mB.getPayable()-(aSt.getExpenses_id().getPricePerUnit()*aSt.getExpenses_id().getQuantity()));
			}
			moneyboxDataFacede.addmoneybox(mB);
		} catch (Exception e) {
			PrimeFaces.current().executeScript("new PNotify({\r\n" + 
					"			title: 'Problem!',\r\n" + 
					"			text: 'Cannot delete this asset!',\r\n" + 
					"			type: 'error',\r\n" + 
					"			left:\"1%\"\r\n" + 
					"		});");
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
		addedAsset.setExpenses_id(addedExpenses);
		paymentAddingMethod=1;
		dateString=null;
		
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/assets/addAsset.jsf");
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
		addedExpenses.setType(1);
		addedExpenses.setPayedOrAddToShares(paymentAddingMethod);
		addedExpenses.setAddedByUser_id(loginBean.getTheUserOfThisAccount());
		addedAsset.setExpenses_id(addedExpenses);
		
		
		
		
		moneybox mB=moneyboxDataFacede.getByUserId(addedExpenses.getBoughtByUser_id().getId());
		if(paymentAddingMethod==1) {
			//Billed method
			if(mB.getMoneyRemains()>(addedAsset.getExpenses_id().getPricePerUnit()*addedAsset.getExpenses_id().getQuantity())) {
				
			mB.setMoneyRemains(mB.getMoneyRemains()-(addedAsset.getExpenses_id().getPricePerUnit()*addedAsset.getExpenses_id().getQuantity()));
			expensesDataFacede.addexpenses(addedExpenses);
			assetDataFacede.addasset(addedAsset);
			moneyboxDataFacede.addmoneybox(mB);
			PrimeFaces.current().executeScript("new PNotify({\r\n" + 
					"			title: 'Success',\r\n" + 
					"			text: 'New asset has been added.',\r\n" + 
					"			type: 'success'\r\n" + 
					"		});");
			
			try {
				FacesContext.getCurrentInstance()
				   .getExternalContext().redirect("/pages/secured/admin/assets/assets.jsf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}else {
				PrimeFaces.current().executeScript("new PNotify({\r\n" + 
						"			title: 'Problem!',\r\n" + 
						"			text: 'The user moneybox has no sufficient Money!',\r\n" + 
						"			type: 'error',\r\n" + 
						"			left:\"1%\"\r\n" + 
						"		});");
			}
			
		}else if(paymentAddingMethod==2){
			//Add to Shared method
			mB.setTotalMoney(mB.getTotalMoney()+(addedAsset.getExpenses_id().getPricePerUnit()*addedAsset.getExpenses_id().getQuantity()));
			expensesDataFacede.addexpenses(addedExpenses);
			assetDataFacede.addasset(addedAsset);
			moneyboxDataFacede.addmoneybox(mB);
			PrimeFaces.current().executeScript("new PNotify({\r\n" + 
					"			title: 'Success',\r\n" + 
					"			text: 'New asset has been added.',\r\n" + 
					"			type: 'success'\r\n" + 
					"		});");
			
			try {
				FacesContext.getCurrentInstance()
				   .getExternalContext().redirect("/pages/secured/admin/assets/assets.jsf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	public moneyboxAppServiceImpl getMoneyboxDataFacede() {
		return moneyboxDataFacede;
	}

	public void setMoneyboxDataFacede(moneyboxAppServiceImpl moneyboxDataFacede) {
		this.moneyboxDataFacede = moneyboxDataFacede;
	}
	
	
	
	
	
}
