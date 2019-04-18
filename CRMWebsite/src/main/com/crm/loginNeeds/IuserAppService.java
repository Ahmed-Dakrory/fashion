/**
 * 
 */
package main.com.crm.loginNeeds;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface IuserAppService {

	public List<user> getAll();
	public user adduser(user data);
	public user getById(int id);
	public user getByEmail(String email);
	public user getByEmailAndPassword(String email,String password);
	public user getByEmailAndPasswordNotActivated(String email,String password);
	public boolean delete(user data);
}
