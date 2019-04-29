/**
 * 
 */
package main.com.crm.loginNeeds;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("userFacadeImpl")
public class userAppServiceImpl implements IuserAppService{

	@Autowired
	userRepository userDataRepository;
	
	
	@Override
	public List<user> getAll() {
		try{
			List<user> course=userDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public user adduser(user data) {
		try{
			user data2=userDataRepository.adduser(data);
			return data2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(user data) {
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
	public user getById(int id) {
		// TODO Auto-generated method stub
		try{
			user so=userDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
	@Override
	public user getByEmailAndPassword(String email,String password) {
		// TODO Auto-generated method stub
		try{
			user so=userDataRepository.getByEmailAndPassword(email,password);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public user getByEmailAndPasswordNotActivated(String email,
			String password) {
		// TODO Auto-generated method stub
				try{
					user so=userDataRepository.getByEmailAndPasswordNotActivated(email,password);
					
					return so;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}



	@Override
	public user getByEmail(String email) {
		try{
			user so=userDataRepository.getByEmail(email);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<user> getAllWithRole(int role) {
		try{
			List<user> course=userDataRepository.getAllWithRole(role);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
}
		
		

	
		
	


