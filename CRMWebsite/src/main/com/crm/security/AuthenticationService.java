package main.com.crm.security;

import javax.annotation.security.RolesAllowed;





public interface AuthenticationService {
	
	boolean login(String username, String password);
	public boolean autoLogin(String username, String password);
	@RolesAllowed({"ROLE_SHAREHOLDER","ROLE_ADMIN"})
	void logout();
}
