/**
 * 
 */
package main.com.crm.rawMaterial;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("rawMaterialFacadeImpl")
public class rawMaterialAppServiceImpl implements IrawMaterialAppService{

	@Autowired
	rawMaterialRepository rawMaterialDataRepository;
	
	
	@Override
	public List<rawMaterial> getAll() {
		try{
			List<rawMaterial> course=rawMaterialDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public rawMaterial addrawMaterial(rawMaterial data) {
		try{
			rawMaterial data2=rawMaterialDataRepository.addrawMaterial(data);
			return data2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(rawMaterial data) throws Exception {
		// TODO Auto-generated method stub
		try{
			rawMaterialDataRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				throw ex;
			}
	}

	@Override
	public rawMaterial getById(int id) {
		// TODO Auto-generated method stub
		try{
			rawMaterial so=rawMaterialDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
	
}
		
		

	
		
	


