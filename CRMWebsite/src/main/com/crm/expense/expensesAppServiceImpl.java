/**
 * 
 */
package main.com.crm.expense;





import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("expensesFacadeImpl")
public class expensesAppServiceImpl implements IexpensesAppService{

	@Autowired
	expensesRepository expensesDataRepository;
	
	
	@Override
	public List<expenses> getAll() {
		try{
			List<expenses> course=expensesDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public expenses addexpenses(expenses data) {
		try{
			expenses data2=expensesDataRepository.addexpenses(data);
			return data2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(expenses data)throws Exception {
		// TODO Auto-generated method stub
		try{
			expensesDataRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				throw ex;
			}
	}

	@Override
	public expenses getById(int id) {
		// TODO Auto-generated method stub
		try{
			expenses so=expensesDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<expenses> getAllExceptType(int type) {
		try{
			List<expenses> course=expensesDataRepository.getAllExceptType(type);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<expenses> getAllForTypeBetweenDateAndRole(Calendar calLower, Calendar calHigher, int expensesType) {
		try{
			List<expenses> course=expensesDataRepository.getAllForTypeBetweenDateAndRole(calLower, calHigher, expensesType);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	

	
}
		
		

	
		
	


