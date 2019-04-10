/**
 * 
 */
package main.com.zc.loginNeeds;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface IUserDataAppService {

	public List<UserData> getAll();
	public UserData addUserData(UserData data);
	public UserData getById(int id);
	public UserData getByEmail(String email);
	public UserData getByEmailAndPassword(String email,String password);
	public UserData getByEmailAndPasswordNotActivated(String email,String password);
	public boolean delete(UserData data);
}
