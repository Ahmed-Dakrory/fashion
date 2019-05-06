/**
 * 
 */
package main.com.crm.productitem;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Ahmed.Dakrory
 *
 */
@Service("productitemFacadeImpl")
public class productitemAppServiceImpl implements IproductitemAppService{

	@Autowired
	productitemRepository productitemDataRepository;
	
	
	@Override
	public List<productitem> getAll() {
		try{
			List<productitem> course=productitemDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public productitem addproductitem(productitem data) {
		try{
			productitem data2=productitemDataRepository.addproductitem(data);
			return data2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(productitem data) throws Exception{
		// TODO Auto-generated method stub
		try{
			productitemDataRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				throw ex;
			}
	}

	@Override
	public productitem getById(int id) {
		// TODO Auto-generated method stub
		try{
			productitem so=productitemDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<productitem> getLastNProducts(int n) {
		try{
			List<productitem> course=productitemDataRepository.getLastNProducts(n);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<productitem> getitemsWithProductId(int idProduct) {
		try{
			List<productitem> course=productitemDataRepository.getitemsWithProductId(idProduct);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<productitem> getAvailableItemsWithQuantity(int product_id,int n_items) {
		try{
			List<productitem> course=productitemDataRepository.getAvailableItemsWithQuantity(product_id,n_items);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<productitem> getitemsWithSaleId(int idSale) {
		try{
			List<productitem> course=productitemDataRepository.getitemsWithSaleId(idSale);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	
}
		
		

	
		
	


