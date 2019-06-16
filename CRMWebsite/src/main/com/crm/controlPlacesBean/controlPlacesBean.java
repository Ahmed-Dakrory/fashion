package main.com.crm.controlPlacesBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import main.com.crm.loginNeeds.user;
import main.com.crm.loginNeeds.userAppServiceImpl;


@ManagedBean(name = "controlPlacesBean")
@SessionScoped
public class controlPlacesBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3409138026523580159L;
	/**
	 * 
	 */
	

	@ManagedProperty(value = "#{userFacadeImpl}")
	private userAppServiceImpl userDataFacede; 
	private user thePlaceOfThisRegisteration;
	
	private List<user> listOfPlace;
	private user selectedPlace;

	public static String DEFAULT_PASSOWRD="123456789";
	public static String DEFAULT_EMAIL="Place@onFashion.com";
	@PostConstruct
	public void init() {
		refresh();
	}
	
	public void refresh(){
		listOfPlace=userDataFacede.getAllWithRole(user.ROLE_PLACE);
		emptyFieldsOFRegisteration();
	}

	public void emptyFieldsOFRegisteration() {
		thePlaceOfThisRegisteration=new user();
	}

	public void selectPlace(int placeId) {
		selectedPlace=userDataFacede.getById(placeId);
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/fashion/pages/secured/admin/controlPlaces/account/placeDetails.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void updatePlace() {
		userDataFacede.adduser(selectedPlace);
		
		
		try {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("/fashion/pages/secured/admin/controlPlaces/account/place.jsf");

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	public void RegisterNewPlace(){
	
		// TODO Auto-generated method stub
			thePlaceOfThisRegisteration.setPassword(new  Md5PasswordEncoder().encodePassword(DEFAULT_PASSOWRD,thePlaceOfThisRegisteration.getEmail()));
			thePlaceOfThisRegisteration.setActive(1);
			String randomEmailForPlace="Place"+thePlaceOfThisRegisteration.getName()+UUID.randomUUID().toString();
			thePlaceOfThisRegisteration.setEmail(randomEmailForPlace);
			thePlaceOfThisRegisteration.setRole(user.ROLE_PLACE);
			userDataFacede.adduser(thePlaceOfThisRegisteration);
			
			
			try {
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("/fashion/pages/secured/admin/controlPlaces/account/place.jsf");

				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	

	
	
	

	public void handleImageUpload(FileUploadEvent event) {

		thePlaceOfThisRegisteration.setImage(event.getFile().getContents());
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	public void handleImageUploadForSelected(FileUploadEvent event) {

		selectedPlace.setImage(event.getFile().getContents());
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	public user getThePlaceOfThisRegisteration() {
		return thePlaceOfThisRegisteration;
	}

	public void setThePlaceOfThisRegisteration(user thePlaceOfThisRegisteration) {
		this.thePlaceOfThisRegisteration = thePlaceOfThisRegisteration;
	}

	
	public userAppServiceImpl getPlaceDataFacede() {
		return userDataFacede;
	}

	public void setPlaceDataFacede(userAppServiceImpl userDataFacede) {
		this.userDataFacede = userDataFacede;
	}


	public List<user> getListOfPlace() {
		return listOfPlace;
	}

	public void setListOfPlace(List<user> listOfPlace) {
		this.listOfPlace = listOfPlace;
	}

	public user getSelectedPlace() {
		return selectedPlace;
	}

	public void setSelectedPlace(user selectedPlace) {
		this.selectedPlace = selectedPlace;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public userAppServiceImpl getUserDataFacede() {
		return userDataFacede;
	}

	public void setUserDataFacede(userAppServiceImpl userDataFacede) {
		this.userDataFacede = userDataFacede;
	}

	
	
}
