package main.com.crm.moneyboxBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import main.com.crm.moneyBox.moneybox;
import main.com.crm.moneyBox.moneyboxAppServiceImpl;
import main.com.crm.loginNeeds.user;
import main.com.crm.loginNeeds.userAppServiceImpl;


@ManagedBean(name = "moneyboxBean")
@SessionScoped
public class moneyboxBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 655527256791420301L;

	
	@ManagedProperty(value = "#{userFacadeImpl}")
	private userAppServiceImpl userDataFacede; 
	
	
	private List<moneybox> listOfMoneybox;
	

	@ManagedProperty(value = "#{moneyboxFacadeImpl}")
	private moneyboxAppServiceImpl moneyboxDataFacede; 
	
	 
	@ManagedProperty(value = "#{loginBean}")
	private main.com.crm.loginNeeds.loginBean loginBean;
	
	
	private moneybox selectedMoneybox;
	
	
	

	private moneybox addedMoneybox;
	private int userId;
	
	List<user> allShareHolders;
	
	@PostConstruct
	public void init() {
		addedMoneybox=new moneybox();
		addedMoneybox.setUser_id(new user());
		
		refresh();
		
	}
	
	public void refresh(){
		listOfMoneybox=moneyboxDataFacede.getAll();
		allShareHolders=userDataFacede.getAllWithRole(user.ROLE_SHAREHOLDER);
		List<user> admin=userDataFacede.getAllWithRole(user.ROLE_ADMIN);
		allShareHolders.addAll(admin);
	}

	
	public void selectMoneybox(int moneyboxId) {
		selectedMoneybox=moneyboxDataFacede.getById(moneyboxId);
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/controlUsers/moneybox/moneyboxDetails.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	 
	     
	    
	     
	
	public void goToAddNewMoneybox() {
		
		addedMoneybox=new moneybox();
		addedMoneybox.setUser_id(new user());
		
		
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/controlUsers/moneybox/addMoneybox.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void updateMoneyBox() {
		
		moneyboxDataFacede.addmoneybox(selectedMoneybox);
		
		PrimeFaces.current().executeScript("new PNotify({\r\n" + 
				"			title: 'Success',\r\n" + 
				"			text: 'Moneybox has been updated.',\r\n" + 
				"			type: 'success'\r\n" + 
				"		});");
		
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/controlUsers/moneybox/moneybox.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addNewMoneybox() {
		user shareHolderUser=userDataFacede.getById(userId);
		addedMoneybox.setUser_id(shareHolderUser);
		moneyboxDataFacede.addmoneybox(addedMoneybox);
		
		PrimeFaces.current().executeScript("new PNotify({\r\n" + 
				"			title: 'Success',\r\n" + 
				"			text: 'New moneybox has been added.',\r\n" + 
				"			type: 'success'\r\n" + 
				"		});");
		
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/controlUsers/moneybox/moneybox.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public userAppServiceImpl getUserDataFacede() {
		return userDataFacede;
	}

	public void setUserDataFacede(userAppServiceImpl userDataFacede) {
		this.userDataFacede = userDataFacede;
	}

	public List<moneybox> getListOfMoneybox() {
		return listOfMoneybox;
	}

	public void setListOfMoneybox(List<moneybox> listOfMoneybox) {
		this.listOfMoneybox = listOfMoneybox;
	}

	public moneyboxAppServiceImpl getMoneyboxDataFacede() {
		return moneyboxDataFacede;
	}

	public void setMoneyboxDataFacede(moneyboxAppServiceImpl moneyboxDataFacede) {
		this.moneyboxDataFacede = moneyboxDataFacede;
	}

	public main.com.crm.loginNeeds.loginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(main.com.crm.loginNeeds.loginBean loginBean) {
		this.loginBean = loginBean;
	}

	public moneybox getSelectedMoneybox() {
		return selectedMoneybox;
	}

	public void setSelectedMoneybox(moneybox selectedMoneybox) {
		this.selectedMoneybox = selectedMoneybox;
	}

	public moneybox getAddedMoneybox() {
		return addedMoneybox;
	}

	public void setAddedMoneybox(moneybox addedMoneybox) {
		this.addedMoneybox = addedMoneybox;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<user> getAllShareHolders() {
		return allShareHolders;
	}

	public void setAllShareHolders(List<user> allShareHolders) {
		this.allShareHolders = allShareHolders;
	}


	
	
	
	
	
}
