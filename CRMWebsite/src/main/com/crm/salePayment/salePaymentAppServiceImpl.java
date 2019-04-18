/**
 * 
 */
package main.com.crm.salePayment;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("salePaymentFacadeImpl")
public class salePaymentAppServiceImpl implements IsalePaymentAppService{

	@Autowired
	salePaymentRepository salePaymentDataRepository;
	
	
	@Override
	public List<salePayment> getAll() {
		try{
			List<salePayment> course=salePaymentDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public salePayment addsalePayment(salePayment data) {
		try{
			salePayment data2=salePaymentDataRepository.addsalePayment(data);
			return data2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(salePayment data) {
		// TODO Auto-generated method stub
		try{
			salePaymentDataRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
	}

	@Override
	public salePayment getById(int id) {
		// TODO Auto-generated method stub
		try{
			salePayment so=salePaymentDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	

	
}
		
		

	
		
	


