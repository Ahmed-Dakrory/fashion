package main.com.crm.controlUsersBean;

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
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import main.com.crm.loginNeeds.user;
import main.com.crm.loginNeeds.userAppServiceImpl;


@ManagedBean(name = "controlUsersBean")
@SessionScoped
public class controlUsersBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3409138026523580159L;
	/**
	 * 
	 */
	

	@ManagedProperty(value = "#{userFacadeImpl}")
	private userAppServiceImpl userDataFacede; 
	private user theUserOfThisRegisteration;
	private String passwordOfRegisteration;
	private String passwordConfirm;
	
	private List<user> listOfUser;
	private user selectedUser;
	
	@PostConstruct
	public void init() {
		refresh();
	}
	
	public void refresh(){
		listOfUser=userDataFacede.getAll();
		emptyFieldsOFRegisteration();
	}

	public void emptyFieldsOFRegisteration() {
		theUserOfThisRegisteration=new user();
		passwordOfRegisteration="";
		passwordConfirm="";
	}

	public void selectUser(int userId) {
		selectedUser=userDataFacede.getById(userId);
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/controlUsers/account/userDetails.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void updateUser() {
		userDataFacede.adduser(selectedUser);
		
		
		try {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("/pages/secured/admin/controlUsers/account/user.jsf");

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
public void RegisterNewUser(){
		
		user u_active =userDataFacede.getByEmail(theUserOfThisRegisteration.getEmail());
		if(u_active!=null) {
		PrimeFaces.current().executeScript("new PNotify({\r\n" + 
				"			title: 'This Account Registered Already!',\r\n" + 
				"			text: 'Please try again with another account or login',\r\n" + 
				"			left:\"2%\"\r\n" + 
				"		});");
		
		}else {
		validateUser(theUserOfThisRegisteration);
		
		}
	}

	private void validateUser(user theUserOfThisAccount2) {
		// TODO Auto-generated method stub
		boolean ok=false;
	
			
		if(passwordOfRegisteration.equals(passwordConfirm)&&!passwordOfRegisteration.equals("")&&passwordOfRegisteration!=null){
			ok=true;
		}
		
		
		if(ok){
			
			completeRegisteration(theUserOfThisAccount2);
			
			
		}else{
			pleaseCheck();
			
		}
	}

	private void pleaseCheck() {
		// TODO Auto-generated method stub
		PrimeFaces.current().executeScript("new PNotify({\r\n" + 
				"			title: 'Check this ',\r\n" + 
				"			text: 'Please Make sure that the Passwords are the same and not empty!',\r\n" + 
				"			left:\"2%\"\r\n" + 
				"		});");
		
	}
	
	private void completeRegisteration(user theUserOfThisAccount2) {
		// TODO Auto-generated method stub
		theUserOfThisAccount2.setPassword(new  Md5PasswordEncoder().encodePassword(passwordOfRegisteration,theUserOfThisAccount2.getEmail()));
		theUserOfThisAccount2.setActive(1);
		userDataFacede.adduser(theUserOfThisAccount2);
		
		
		try {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("/pages/secured/admin/controlUsers/account/user.jsf");

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	public void handleImageUpload(FileUploadEvent event) {

		theUserOfThisRegisteration.setImage(event.getFile().getContents());
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	public void handleImageUploadForSelected(FileUploadEvent event) {

		selectedUser.setImage(event.getFile().getContents());
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	public user getTheUserOfThisRegisteration() {
		return theUserOfThisRegisteration;
	}

	public void setTheUserOfThisRegisteration(user theUserOfThisRegisteration) {
		this.theUserOfThisRegisteration = theUserOfThisRegisteration;
	}

	public String getPasswordOfRegisteration() {
		return passwordOfRegisteration;
	}

	public void setPasswordOfRegisteration(String passwordOfRegisteration) {
		this.passwordOfRegisteration = passwordOfRegisteration;
	}

	public userAppServiceImpl getUserDataFacede() {
		return userDataFacede;
	}

	public void setUserDataFacede(userAppServiceImpl userDataFacede) {
		this.userDataFacede = userDataFacede;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public List<user> getListOfUser() {
		return listOfUser;
	}

	public void setListOfUser(List<user> listOfUser) {
		this.listOfUser = listOfUser;
	}

	public user getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(user selectedUser) {
		this.selectedUser = selectedUser;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
