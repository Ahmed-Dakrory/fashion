/**
 * 
 */
package main.com.crm.customer;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("customerFacadeImpl")
public class customerAppServiceImpl implements IcustomerAppService{

	@Autowired
	customerRepository customerDataRepository;
	
	
	@Override
	public List<customer> getAll() {
		try{
			List<customer> course=customerDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public customer addcustomer(customer data) {
		try{
			customer data2=customerDataRepository.addcustomer(data);
			return data2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(customer data) {
		// TODO Auto-generated method stub
		try{
			customerDataRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
	}

	@Override
	public customer getById(int id) {
		// TODO Auto-generated method stub
		try{
			customer so=customerDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	

	
}
		
		

	
		
	


