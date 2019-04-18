/**
 * 
 */
package main.com.crm.asset;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("assetFacadeImpl")
public class assetAppServiceImpl implements IassetAppService{

	@Autowired
	assetRepository assetDataRepository;
	
	
	@Override
	public List<asset> getAll() {
		try{
			List<asset> course=assetDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public asset addasset(asset data) {
		try{
			asset data2=assetDataRepository.addasset(data);
			return data2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(asset data) {
		// TODO Auto-generated method stub
		try{
			assetDataRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
	}

	@Override
	public asset getById(int id) {
		// TODO Auto-generated method stub
		try{
			asset so=assetDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
	
}
		
		

	
		
	


