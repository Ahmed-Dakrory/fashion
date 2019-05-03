package main.com.crm.loginNeeds;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.PrimeFaces;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import main.com.crm.security.AuthenticationService;


@ManagedBean(name = "loginBean")
@SessionScoped
public class loginBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6715784400190397743L;
	private boolean isLoggedIn;
	private String emailOfUserLoggedIn;
	private String passwordOfUserLoggedIn;
	private String passwordOfRegisteration;
	private List<user> listOfUsers;
	private user theUserOfThisAccount;
	private int type;
	

	@ManagedProperty(value = "#{userFacadeImpl}")
	private userAppServiceImpl userDataFacede; 
	 

	private Boolean rememberMe;
	@ManagedProperty(value = "#{authenticationService}")
	private AuthenticationService authenticationService;
	
	
	private String passwordConfirm;
	@PostConstruct
	public void init() {
		isLoggedIn=false;
		theUserOfThisAccount=new user();
		//theUserOfThisAccount=userDataFacede.getByEmailAndPassword(emailOfUserLoggedIn, passwordOfUserLoggedIn);
		listOfUsers=userDataFacede.getAll();
		
		
	}
	
	public void refresh(){
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.getRequest();
		
		try{
			Integer id=Integer.parseInt(origRequest.getParameterValues("id")[0]);
				if(id!=null){
					user user=getuserFacede().getById(id);
					user.setActive(1);
					getuserFacede().adduser(user);
					
				}
			}
		catch(Exception ex){
			 
		}
	}
	
	public String logOut(){

		emailOfUserLoggedIn="";
		passwordOfUserLoggedIn="";
		authenticationService.logout();
		theUserOfThisAccount=new user();
		isLoggedIn=false;
		System.out.println("");
		return "/pages/public/index.jsf?faces-redirect=true";
	}
	public void login(){

		 String hashedPassword= new  Md5PasswordEncoder().encodePassword(passwordOfUserLoggedIn,emailOfUserLoggedIn);

		theUserOfThisAccount = userDataFacede.getByEmailAndPassword(emailOfUserLoggedIn,hashedPassword);

		if(theUserOfThisAccount!=null){
			isLoggedIn=true;
			
		}else{
			isLoggedIn=false;
			theUserOfThisAccount=new user();
			wrongPassword();
		}
		if(isLoggedIn){
			

			
						boolean success = authenticationService.login(theUserOfThisAccount.getEmail(), passwordOfUserLoggedIn);
						if (success) {

								FacesContext.getCurrentInstance().getExternalContext()
											.getSessionMap().put("resetMenu", true);
									

			try {
				if(theUserOfThisAccount.getRole()==user.ROLE_SHAREHOLDER) {
					FacesContext.getCurrentInstance()
					   .getExternalContext().redirect("/pages/secured/admin/home.jsf");
				}else if(theUserOfThisAccount.getRole()==user.ROLE_ADMIN) {
						FacesContext.getCurrentInstance()
						   .getExternalContext().redirect("/pages/secured/admin/home.jsf");
				}else {
				FacesContext.getCurrentInstance()
					   .getExternalContext().redirect("/");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
							}
		}else{
			

			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("FormMain");
		}
		
	}
	

	
   
	public void wrongPassword(){
		PrimeFaces.current().executeScript("new PNotify({\r\n" + 
				"			title: 'wrong Credentials!',\r\n" + 
				"			text: 'Please try Again!',\r\n" + 
				"			type: 'error',\r\n" + 
				"			left:\"1%\"\r\n" + 
				"		});");
		
	
	}
	
	public void updateDataOfUser() {
		
		validateUser(theUserOfThisAccount);
		
	}
	
	
	
	private void validateUser(user theUserOfThisAccount2) {
		// TODO Auto-generated method stub
		boolean ok=false;

			
		if(passwordOfRegisteration.equals(passwordConfirm)&&!passwordOfRegisteration.equals("")&&passwordOfRegisteration!=null){
			ok=true;
		}
		
		
		if(ok){
			
				theUserOfThisAccount2.setPassword(new  Md5PasswordEncoder().encodePassword(passwordOfRegisteration,theUserOfThisAccount2.getEmail()));
				userDataFacede.adduser(theUserOfThisAccount2);
				PrimeFaces.current().executeScript("new PNotify({\r\n" + 
						"			title: 'Success',\r\n" + 
						"			text: 'Your data has been changed.',\r\n" + 
						"			type: 'success'\r\n" + 
						"		});");
			
			
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

	
	public String getTheStatueOfLoginMenu(){
		if(isLoggedIn){
			return "inherit";
		}
		return "none";
	}
	
	
	
	
	
	public String getTheStatueMenuMain(){
		if(isLoggedIn){
			return "none";
		}
		return "block";
	}
	
	
	
	/*
	 * the start of getting and setting method
	 */
	
	
	public String getEmailOfUserLoggedIn() {
		return emailOfUserLoggedIn;
	}

	

	
	public void setEmailOfUserLoggedIn(String emailOfUserLoggedIn) {
		this.emailOfUserLoggedIn = emailOfUserLoggedIn;
	}

	public String getPasswordOfUserLoggedIn() {
		return passwordOfUserLoggedIn;
	}

	public void setPasswordOfUserLoggedIn(String passwordOfUserLoggedIn) {
		this.passwordOfUserLoggedIn = passwordOfUserLoggedIn;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}
	
	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}


	public List<user> getListOfUsers() {
		return listOfUsers;
	}


	public void setListOfUsers(List<user> listOfUsers) {
		this.listOfUsers = listOfUsers;
	}


	public user getTheUserOfThisAccount() {
		return theUserOfThisAccount;
	}


	public void setTheUserOfThisAccount(user theUserOfThisAccount) {
		this.theUserOfThisAccount = theUserOfThisAccount;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public userAppServiceImpl getuserFacede() {
		return userDataFacede;
	}


	public void setuserFacede(userAppServiceImpl userDataFacede) {
		this.userDataFacede = userDataFacede;
	}


	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public Boolean getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public AuthenticationService getAuthenticationService() {
		return authenticationService;
	}

	public void setAuthenticationService(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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


	
	
	
	
	
	
	
	

	
}
