package main.com.zc.loginNeeds;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import main.com.zc.security.AuthenticationService;


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
	private List<UserData> listOfUsers;
	private UserData theUserOfThisAccount;
	private UserData theUserOfThisRegisteration;
	private int type;
	private String imageOfAccountUser;
	private byte[] imageOfReg;
	private boolean imageUploaded;
	
	private String imageOfAccountUser_reg;
	private byte[] imageOfReg_reg;
	private boolean imageUploaded_reg;
	
	

	@ManagedProperty(value = "#{UserDataFacadeImpl}")
	private UserDataAppServiceImpl userDataFacede; 
	 

	private Boolean rememberMe;
	@ManagedProperty(value = "#{authenticationService}")
	private AuthenticationService authenticationService;
	
	
	private String passwordConfirm;
	@PostConstruct
	public void init() {
		imageOfAccountUser="images/comment-img.jpg";
		imageOfAccountUser_reg="images/comment-img.jpg";
		isLoggedIn=false;
		imageUploaded=false;
		imageUploaded_reg=false;
		theUserOfThisAccount=new UserData();
		setImageDependOnLoginState();
		//theUserOfThisAccount=userDataFacede.getByEmailAndPassword(emailOfUserLoggedIn, passwordOfUserLoggedIn);
		listOfUsers=userDataFacede.getAll();
		
		
	}
	public void emptyFieldsOFRegisteration() {
		imageOfAccountUser_reg="images/comment-img.jpg";
		theUserOfThisRegisteration=new UserData();
		passwordConfirm="";
		passwordOfRegisteration="";
	}
	public void refresh(){
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.getRequest();
		
		try{
			Integer id=Integer.parseInt(origRequest.getParameterValues("id")[0]);
				if(id!=null){
					UserData user=getUserDataFacede().getById(id);
					user.setActive(1);
					getUserDataFacede().addUserData(user);
					
				}
			}
		catch(Exception ex){
			 
		}
	}
	
	public String logOut(){

		imageOfAccountUser="images/comment-img.jpg";
		emailOfUserLoggedIn="";
		passwordOfUserLoggedIn="";
		authenticationService.logout();
		theUserOfThisAccount=new UserData();
		isLoggedIn=false;
		setImageDependOnLoginState();
		imageUploaded=false;
		System.out.println("");
		return "/pages/public/index.xhtml?faces-redirect=true";
	}
	public void login(){

		 String hashedPassword= new  Md5PasswordEncoder().encodePassword(passwordOfUserLoggedIn,emailOfUserLoggedIn);

		theUserOfThisAccount = userDataFacede.getByEmailAndPassword(emailOfUserLoggedIn,hashedPassword);

		if(theUserOfThisAccount!=null){
			isLoggedIn=true;
			
		}else{
			isLoggedIn=false;
			theUserOfThisAccount=new UserData();
			wrongPassword();
		}
		if(isLoggedIn){
			setImageDependOnLoginState();
			

			
						boolean success = authenticationService.login(theUserOfThisAccount.getEmail(), passwordOfUserLoggedIn);
						if (success) {

								FacesContext.getCurrentInstance().getExternalContext()
											.getSessionMap().put("resetMenu", true);
									

			try {
				if(theUserOfThisAccount.getEmail().equals("lts-admin@zewailcity.edu.eg")) {
				FacesContext.getCurrentInstance()
				   .getExternalContext().redirect("/pages/secured/admin/adminController.xhtml");
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
		PrimeFaces.current().executeScript("swal({\r\n" + 
				"  title: \"wrong Credentials!\",\r\n" + 
				"  text: \"Please try Again!\",\r\n" + 
				"  icon: \"warning\",\r\n" + 
				"})\r\n" + 
				";");
		
   
	}
	
	void setImageDependOnLoginState(){
		if(isLoggedIn&&theUserOfThisAccount.getImage()!=null&&theUserOfThisAccount.getImage().length!=0){
			imageOfAccountUser=theUserOfThisAccount.getphoto();
		}else{
			
			imageOfAccountUser="images/comment-img.jpg";
		}
	}
	
	void setImageDependOnRegisterationImageState(){
		if(imageUploaded){
			imageOfAccountUser=theUserOfThisAccount.getphoto();
		}else{
			
			imageOfAccountUser="images/comment-img.jpg";
		}
	}
	
	void setImageDependOnRegisterationImageState_reg(){
		if(imageUploaded_reg){
			imageOfAccountUser_reg=theUserOfThisRegisteration.getphoto();
		}else{
			
			imageOfAccountUser_reg="images/comment-img.jpg";
		}
	}
	public void RegisterNewUser(){

		
		UserData u_active =userDataFacede.getByEmail(theUserOfThisRegisteration.getEmail());
		if(u_active!=null) {
		PrimeFaces.current().executeScript("swal({\r\n" + 
				"  title: \"This Account Registered Already!\",\r\n" + 
				"  text: \"Please try again with another account or login\",\r\n" + 
				"  icon: \"warning\",\r\n" + 
				"})\r\n" + 
				";");
		}else {
		validateUser(theUserOfThisRegisteration);
		
		}
	}
	
	private void validateUser(UserData theUserOfThisAccount2) {
		// TODO Auto-generated method stub
		boolean ok=false;

			
		if(passwordOfRegisteration.equals(passwordConfirm)){
			ok=true;
		}
		
		
		if(ok){
			//PrimeFaces.current().executeScript("document.getElementById(\"fullScreenLoader\").style.display = \"block\";");
			
			completeRegisteration();

			
		}else{
			pleaseCheck();
			
		}
	}

	private void pleaseCheck() {
		// TODO Auto-generated method stub
		PrimeFaces.current().executeScript("swal({\r\n" + 
				"  title: \"Check this \",\r\n" + 
				"  text: \"Please Make sure that the Passwords are the same!\",\r\n" + 
				"  icon: \"warning\",\r\n" + 
				"})\r\n" + 
				";");
	
	}

	private void completeRegisteration() {
		// TODO Auto-generated method stub
		theUserOfThisRegisteration.setPassword(new  Md5PasswordEncoder().encodePassword(passwordOfRegisteration,theUserOfThisRegisteration.getEmail()));
		theUserOfThisRegisteration.setImage(imageOfReg_reg);
		theUserOfThisRegisteration.setActive(0);
		theUserOfThisRegisteration.setMode(1);
		userDataFacede.addUserData(theUserOfThisRegisteration);
		
		
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("registerEmailConfirm.xhtml");

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		activateAccount(theUserOfThisRegisteration);
	}

	public void activateAccount(UserData userdata){
		sendEmailWithActivationCode(userdata);
	}
	 private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
	        Properties props = System.getProperties();
	        String host = "smtp.gmail.com";
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", host);
	        props.put("mail.smtp.user", from);
	        props.put("mail.smtp.password", pass);
	        props.put("mail.smtp.port", "587");
	        props.put("mail.smtp.auth", "true");

	        Session session = Session.getDefaultInstance(props);
	        MimeMessage message = new MimeMessage(session);

	        try {
	            message.setFrom(new InternetAddress(from));
	            InternetAddress[] toAddress = new InternetAddress[to.length];

	            // To get the array of addresses
	            for( int i = 0; i < to.length; i++ ) {
	                toAddress[i] = new InternetAddress(to[i]);
	            }

	            for( int i = 0; i < toAddress.length; i++) {
	                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
	            }

	            message.setSubject(subject);
	            message.setText(body);

	    		message.setContent(body, "text/html; charset=ISO-8859-1");
	            Transport transport = session.getTransport("smtp");
	            transport.connect(host, from, pass);
	            transport.sendMessage(message, message.getAllRecipients());
	            transport.close();
	        }
	        catch (AddressException ae) {
	            ae.printStackTrace();
	        }
	        catch (MessagingException me) {
	            me.printStackTrace();
	        }
	    }
	 
	 
	public void sendEmailWithActivationCode(UserData userdata1){
		UserData userdata=userDataFacede.getByEmailAndPasswordNotActivated(userdata1.getEmail(), userdata1.getPassword());
		
		
		 String from = "learningtechnologies@zewailcity.edu.eg";
	        String pass = "zcltinfo";
	        String[] to = { theUserOfThisRegisteration.getEmail() }; // list of recipient email addresses
	        String subject = "A Comfirmation Email for your Account";
	        String htmlText = "<div style=width:700px;margin:0 auto;font:normal 13px/30px Segoe, Segoe UI, DejaVu Sans, Trebuchet MS, Verdana, sans-serif !important;>"
					+ "<ul style=margin:0;padding:0;>"
					+ "<li style=list-style:none;float:left;width:700px;margin:0;>"
					+ "	<ul style=margin:0;padding:0;width:700px;margin-top:18px;>"
					+ "<li style=list-style:none;float:left;width:260px;padding:0;><img src=\\\"http://outreach.zclt.info/javax.faces.resource/images/logoZc.png.xhtml\\\" style=\\\"width: 15%;\\\" alt=Zewail City of Science and Technology /></li>"
					+ "</ul>"
					+ "</li>"
					+ "<li style=list-style:none;float:left;width:700px;background:#f1f2f2;margin:15px 0 24px 0;padding:1px 0;>&nbsp;</li>"
					+ "<li style=list-style:none;float:left;width:700px;margin-bottom:24px;padding-left:24px;>"
					+ "<h2 style=margin:0;padding:0;color:#404040 !important;>Outreach and Engagment</h2>"
					+ "</li>"
					+ "<li style=list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;>"
					+ "<div style=padding:24px 36px;color:#676767 !important;>"
					+ "<span style=color:#676767>Dear "
					+ userdata.getFullName()
					+ ",</span><br/><br/><br/>"
					+ "<span style=color:#676767>please click this link to activate account "+"http://outreach.zclt.info/pages/public/login.xhtml?id="+userdata.getId()+"</span><br/><br/><br/>"
					+ "</span><br/><br/>"
					+ "<span style=color:#676767>Thank you, </span><br/><br/>"
					+ "<span style=color:#676767>Outreach and Engagment</span>"
					+ "</div>"
					+ "</li>"
					+ "<li style=list-style:none;float:left;width:700px;margin-bottom:4px;background:#ececec;>"
					+ "<ul style=margin:0;padding:0;>"
					+ "<li style=list-style:none;float:left;width:134px;margin:0;padding:18px 36px !important;color:#717070;>"
					+ "<a href=http://outreach.zclt.info title=Center for Learning Technologies><img src=\\\"http://outreach.zclt.info/javax.faces.resource/images/logoZc.png.xhtml\\\" style=\\\"width: 15%;\\\" alt=Center for Learning Technologies /></a><br/>"
					+ "<span style=color:#404040;font-size:11px;>Giving Fuel to Innovation</span>"
					+ "</li>"
					+ "<li style=list-style:none;float:right;width:59px;margin:0;padding:18px 36px !important;color:#717070;>"
					+ "<a href=http://outreach.zclt.info title=Zewail City of Science and Technology><img src=\\\"http://outreach.zclt.info/javax.faces.resource/images/logoZc.png.xhtml\\\" style=\\\"width: 15%;\\\"  alt=Zewail City of Science and Technology /></a>"
					+ "</li>"
					+ "</ul>"
					+ "</li>"
					+ "<li style=list-style:none;float:left;width:700px;margin-bottom:12px;background:#ececec;>"
					+ "<div style=padding:8px 16px;color:#a1a0a0;font-size:11px;line-height:20px;>"
					+ " <br/><b><span style=color:#a1a0a0;font-size:11px;>Follow us:</sapn></b><a href=https://www.facebook.com/learning.technologies.zewailcity title=ZC LT Facebook><img src=\"http://zclt.info/ZCTestMail/facebook_square.png\"  alt=ZC LT Facebook style=vertical-align:middle;/></a>"
					+ "  <a href=https://www.youtube.com/channel/UCiajXXIv0rCpxVIgCDekm2A title=ZC LT Youtube><img src=\"http://zclt.info/ZCTestMail/youtube_square.png\"   alt=ZC LT Youtube style=vertical-align:middle;/></a>"
					+ "</div>" + "</li>" + "</ul>" + "</div>";

	        sendFromGMail(from, pass, to, subject, htmlText);
	        
	 
		theUserOfThisRegisteration=new UserData();
		imageUploaded_reg=false;
		imageOfAccountUser_reg="images/comment-img.jpg";

	}
	public String getTheStatueOfLoginMenu(){
		if(isLoggedIn){
			return "inherit";
		}
		return "none";
	}
	
	public String getTheStatueOfImageMenu(){
		if(isLoggedIn&&theUserOfThisAccount.getImage()==null){
			return "block";
		}else if(isLoggedIn){
			return "none";
		}
		return "inherit";
	}
	
	public String getTheStatueOfImageLoginMenu(){
		if(isLoggedIn&&theUserOfThisAccount.getImage()==null){
			return "none";
		}else if(isLoggedIn){
			return "inherit";
		}
		return "none";
	}
	public String imageUploadedVisib(){
		if(imageUploaded){
			return "block";
		}
		return "none";
	}
	
	public String imageUploadedVisib_reg(){
		if(imageUploaded_reg){
			return "block";
		}
		return "none";
	}
	
	public String imageUploadedVisibNot(){
		if(!imageUploaded){
			return "block";
		}
		return "none";
	}
	
	public String imageUploadedVisibNot_reg(){
		if(!imageUploaded_reg){
			return "block";
		}
		return "none";
	}
	
	public String getTheStatueMenuMain(){
		if(isLoggedIn){
			return "none";
		}
		return "block";
	}
	
	
	
	public void previewImage(FileUploadEvent event) {
		/* FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		*/
		this.imageOfReg = event.getFile().getContents();
		theUserOfThisAccount.setImage(imageOfReg);
		imageUploaded=true;
		setImageDependOnRegisterationImageState();
		System.out.println("File Uploaded");
		
	}

	public void previewImage_reg(FileUploadEvent event) {
		/* FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		*/
		this.imageOfReg_reg = event.getFile().getContents();
		theUserOfThisRegisteration.setImage(imageOfReg_reg);
		imageUploaded_reg=true;
		setImageDependOnRegisterationImageState_reg();
		System.out.println("File Uploaded");
		
	}

	public String getTheVisibityOfAdmin() {
		if(theUserOfThisAccount!=null&&theUserOfThisAccount.getMode()!=null) {
			if(theUserOfThisAccount.getMode()==0) {
				
				return "inherit";
			}else {
				return "hidden";
			}
		}else {
			return "hidden";
		}
		
	}
	/*
	 * the start of getting and setting method
	 */
	
	
	public String getEmailOfUserLoggedIn() {
		return emailOfUserLoggedIn;
	}

	

	public byte[] getImageOfReg() {
		return imageOfReg;
	}

	public void setImageOfReg(byte[] imageOfReg) {
		this.imageOfReg = imageOfReg;
	}

	public boolean isImageUploaded() {
		return imageUploaded;
	}

	public void setImageUploaded(boolean imageUploaded) {
		this.imageUploaded = imageUploaded;
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


	public List<UserData> getListOfUsers() {
		return listOfUsers;
	}


	public void setListOfUsers(List<UserData> listOfUsers) {
		this.listOfUsers = listOfUsers;
	}


	public UserData getTheUserOfThisAccount() {
		return theUserOfThisAccount;
	}


	public void setTheUserOfThisAccount(UserData theUserOfThisAccount) {
		this.theUserOfThisAccount = theUserOfThisAccount;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public UserDataAppServiceImpl getUserDataFacede() {
		return userDataFacede;
	}


	public void setUserDataFacede(UserDataAppServiceImpl userDataFacede) {
		this.userDataFacede = userDataFacede;
	}

	public String getImageOfAccountUser() {
		return imageOfAccountUser;
	}

	public void setImageOfAccountUser(String imageOfAccountUser) {
		this.imageOfAccountUser = imageOfAccountUser;
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
	public UserData getTheUserOfThisRegisteration() {
		return theUserOfThisRegisteration;
	}
	public void setTheUserOfThisRegisteration(UserData theUserOfThisRegisteration) {
		this.theUserOfThisRegisteration = theUserOfThisRegisteration;
	}
	public String getImageOfAccountUser_reg() {
		return imageOfAccountUser_reg;
	}
	public void setImageOfAccountUser_reg(String imageOfAccountUser_reg) {
		this.imageOfAccountUser_reg = imageOfAccountUser_reg;
	}
	public byte[] getImageOfReg_reg() {
		return imageOfReg_reg;
	}
	public void setImageOfReg_reg(byte[] imageOfReg_reg) {
		this.imageOfReg_reg = imageOfReg_reg;
	}
	public boolean isImageUploaded_reg() {
		return imageUploaded_reg;
	}
	public void setImageUploaded_reg(boolean imageUploaded_reg) {
		this.imageUploaded_reg = imageUploaded_reg;
	}


	
	
	
	
	
	
	
	

	
}
