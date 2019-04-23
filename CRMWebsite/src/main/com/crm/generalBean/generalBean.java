package main.com.crm.generalBean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;

import main.com.crm.loginNeeds.user;
import main.com.crm.loginNeeds.userAppServiceImpl;


@ManagedBean(name = "generalBean")
@SessionScoped
public class generalBean implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7383039904260474699L;


	/**
	 * 
	 */
	
	@ManagedProperty(value = "#{loginBean}")
	private main.com.crm.loginNeeds.loginBean loginBean;
	
	
	
	@ManagedProperty(value = "#{userFacadeImpl}")
	private userAppServiceImpl userDataFacede; 
	
	private user selectedUser;
	
	@PostConstruct
	public void init() {
		
		refresh();
		
	}
	
	public void refresh(){
		
	}


	public void showUser(int idUser) {
		selectedUser=userDataFacede.getById(idUser);
		Map<String,Object> options = new HashMap<String, Object>();
	    options.put("modal", true);
        options.put("width", 400);
        options.put("height", 550);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("background", "#ffdead00");
	    PrimeFaces.current().dialog().openDynamic("/pages/secured/admin/general/userDetails", options, null);
		
	}

	public void doSomethingAndCloseDialog1() {
		
		RequestContext.getCurrentInstance().closeDialog("/pages/secured/admin/general/userDetails");
    }
	
	public main.com.crm.loginNeeds.loginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(main.com.crm.loginNeeds.loginBean loginBean) {
		this.loginBean = loginBean;
	}

	public userAppServiceImpl getUserDataFacede() {
		return userDataFacede;
	}

	public void setUserDataFacede(userAppServiceImpl userDataFacede) {
		this.userDataFacede = userDataFacede;
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
