/**
 * 
 */
package main.com.crm.product;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Ahmed.Dakrory
 *
 */
@Service("productFacadeImpl")
public class productAppServiceImpl implements IproductAppService{

	@Autowired
	productRepository productDataRepository;
	
	
	@Override
	public List<product> getAll() {
		try{
			List<product> course=productDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public product addproduct(product data) {
		try{
			product data2=productDataRepository.addproduct(data);
			return data2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(product data) throws Exception{
		// TODO Auto-generated method stub
		try{
			productDataRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				throw ex;
			}
	}

	@Override
	public product getById(int id) {
		// TODO Auto-generated method stub
		try{
			product so=productDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<product> getLastNProducts(int n) {
		try{
			List<product> course=productDataRepository.getLastNProducts(n);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	
}
		
		

	
		
	


