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
import main.com.crm.moneyBox.moneybox;
import main.com.crm.moneyBox.moneyboxAppServiceImpl;


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
	
	@ManagedProperty(value = "#{moneyboxFacadeImpl}")
	private moneyboxAppServiceImpl moneyboxDataFacede; 
	 
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
		allUsers=userDataFacede.getAllWithRole(user.ROLE_SHAREHOLDER);
		allUsers.addAll(userDataFacede.getAllWithRole(user.ROLE_ADMIN));
		listOfrawMaterials=rawMaterialDataFacede.getAll();
	}

	
	public void selectrawMaterial(int rawMaterialId) {
		selectedrawMaterial=rawMaterialDataFacede.getById(rawMaterialId);
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/fashion/pages/secured/admin/rawMaterial/rawMaterialDetails.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	 public void deleteRawMaterial(int idRaw) {
		 rawMaterial rM=rawMaterialDataFacede.getById(idRaw);
		 
		 try {
			rawMaterialDataFacede.delete(rM);
			expensesDataFacede.delete(rM.getExpenses_id());
			moneybox mB=moneyboxDataFacede.getByUserId(rM.getExpenses_id().getBoughtByUser_id().getId());
			if(rM.getExpenses_id().getStatues()==1) {
				// he pay this amount so we need retrieve it
				if(rM.getExpenses_id().getPayedOrAddToShares()==1) {
					//Payed directly
					mB.setMoneyRemains(mB.getMoneyRemains()+(rM.getExpenses_id().getPricePerUnit()*rM.getExpenses_id().getQuantity()));
					
				}else {
					//Payed as shares
					mB.setTotalMoney(mB.getTotalMoney()-(rM.getExpenses_id().getPricePerUnit()*rM.getExpenses_id().getQuantity()));
					
				}
			}else {
				// pay as payable
				mB.setTotalMoney(mB.getTotalMoney()-(rM.getExpenses_id().getPricePerUnit()*rM.getExpenses_id().getQuantity()));
				mB.setPayable(mB.getPayable()-(rM.getExpenses_id().getPricePerUnit()*rM.getExpenses_id().getQuantity()));
			}
			moneyboxDataFacede.addmoneybox(mB);
		} catch (Exception e) {
			PrimeFaces.current().executeScript("new PNotify({\r\n" + 
					"			title: 'Problem!',\r\n" + 
					"			text: 'Cannot delete this material, some products related to it!',\r\n" + 
					"			type: 'error',\r\n" + 
					"			left:\"1%\"\r\n" + 
					"		});");
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
		addedrawMaterial.setAvailableQuantity(addedrawMaterial.getExpenses_id().getQuantity());
		paymentAddingMethod=1;
		unit=1;
		dateString=null;
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/fashion/pages/secured/admin/rawMaterial/addrawMaterial.jsf");
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
		addedExpenses.setType(2);
		addedExpenses.setPayedOrAddToShares(paymentAddingMethod);
		addedExpenses.setAddedByUser_id(loginBean.getTheUserOfThisAccount());
		addedrawMaterial.setUnit(unit);
		addedrawMaterial.setExpenses_id(addedExpenses);
		addedrawMaterial.setAvailableQuantity(addedrawMaterial.getExpenses_id().getQuantity());
		
		
		
		moneybox mB=moneyboxDataFacede.getByUserId(addedExpenses.getBoughtByUser_id().getId());
		
		if(paymentAddingMethod==1) {
			//Billed method
			if(mB.getMoneyRemains()>(addedrawMaterial.getExpenses_id().getPricePerUnit()*addedrawMaterial.getExpenses_id().getQuantity())) {
				mB.setMoneyRemains(mB.getMoneyRemains()-(addedrawMaterial.getExpenses_id().getPricePerUnit()*addedrawMaterial.getExpenses_id().getQuantity()));
				
				expensesDataFacede.addexpenses(addedExpenses);
				rawMaterialDataFacede.addrawMaterial(addedrawMaterial);
				
				moneyboxDataFacede.addmoneybox(mB);
				PrimeFaces.current().executeScript("new PNotify({\r\n" + 
						"			title: 'Success',\r\n" + 
						"			text: 'New rawMaterial has been added.',\r\n" + 
						"			type: 'success'\r\n" + 
						"		});");
				
				try {
					FacesContext.getCurrentInstance()
					   .getExternalContext().redirect("/fashion/pages/secured/admin/rawMaterial/rawMaterials.jsf");
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
			mB.setTotalMoney(mB.getTotalMoney()+(addedrawMaterial.getExpenses_id().getPricePerUnit()*addedrawMaterial.getExpenses_id().getQuantity()));
			
			expensesDataFacede.addexpenses(addedExpenses);
			rawMaterialDataFacede.addrawMaterial(addedrawMaterial);
			
			moneyboxDataFacede.addmoneybox(mB);
			PrimeFaces.current().executeScript("new PNotify({\r\n" + 
					"			title: 'Success',\r\n" + 
					"			text: 'New rawMaterial has been added.',\r\n" + 
					"			type: 'success'\r\n" + 
					"		});");
			
			try {
				FacesContext.getCurrentInstance()
				   .getExternalContext().redirect("/fashion/pages/secured/admin/rawMaterial/rawMaterials.jsf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	public moneyboxAppServiceImpl getMoneyboxDataFacede() {
		return moneyboxDataFacede;
	}

	public void setMoneyboxDataFacede(moneyboxAppServiceImpl moneyboxDataFacede) {
		this.moneyboxDataFacede = moneyboxDataFacede;
	}
	
	
	
	
	
}
