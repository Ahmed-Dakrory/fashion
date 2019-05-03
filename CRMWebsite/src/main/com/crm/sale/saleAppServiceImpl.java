/**
 * 
 */
package main.com.crm.sale;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("saleFacadeImpl")
public class saleAppServiceImpl implements IsaleAppService{

	@Autowired
	saleRepository saleDataRepository;
	
	
	@Override
	public List<sale> getAll() {
		try{
			List<sale> course=saleDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public sale addsale(sale data) {
		try{
			sale data2=saleDataRepository.addsale(data);
			return data2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(sale data) {
		// TODO Auto-generated method stub
		try{
			saleDataRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
	}

	@Override
	public sale getById(int id) {
		// TODO Auto-generated method stub
		try{
			sale so=saleDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<sale> getAllProductById(int idProduct) {
		try{
			List<sale> course=saleDataRepository.getAllProductById(idProduct);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	

	
}
		
		

	
		
	


