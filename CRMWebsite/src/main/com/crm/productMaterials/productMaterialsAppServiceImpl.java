/**
 * 
 */
package main.com.crm.productMaterials;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("productMaterialsFacadeImpl")
public class productMaterialsAppServiceImpl implements IproductMaterialsAppService{

	@Autowired
	productMaterialsRepository productMaterialsDataRepository;
	
	
	@Override
	public List<productMaterials> getAll() {
		try{
			List<productMaterials> course=productMaterialsDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public productMaterials addproductMaterials(productMaterials data) {
		try{
			productMaterials data2=productMaterialsDataRepository.addproductMaterials(data);
			return data2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(productMaterials data) {
		// TODO Auto-generated method stub
		try{
			productMaterialsDataRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
	}

	@Override
	public productMaterials getById(int id) {
		// TODO Auto-generated method stub
		try{
			productMaterials so=productMaterialsDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	

	
}
		
		

	
		
	


