/**
 * 
 */
package main.com.zc.loginNeeds;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("UserDataFacadeImpl")
public class UserDataAppServiceImpl implements IUserDataAppService{

	@Autowired
	UserDataRepository userDataRepository;
	
	
	@Override
	public List<UserData> getAll() {
		try{
			List<UserData> course=userDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public UserData addUserData(UserData data) {
		try{
			UserData data2=userDataRepository.addUserData(data);
			return data2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(UserData data) {
		// TODO Auto-generated method stub
		try{
			userDataRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
	}

	@Override
	public UserData getById(int id) {
		// TODO Auto-generated method stub
		try{
			UserData so=userDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
	@Override
	public UserData getByEmailAndPassword(String email,String password) {
		// TODO Auto-generated method stub
		try{
			UserData so=userDataRepository.getByEmailAndPassword(email,password);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public UserData getByEmailAndPasswordNotActivated(String email,
			String password) {
		// TODO Auto-generated method stub
				try{
					UserData so=userDataRepository.getByEmailAndPasswordNotActivated(email,password);
					
					return so;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}



	@Override
	public UserData getByEmail(String email) {
		try{
			UserData so=userDataRepository.getByEmail(email);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
}
		
		

	
		
	


