/**
 * 
 */
package main.com.crm.salePayment;

import java.util.Calendar;
import java.util.List;

/**
 * 
 * @author Ahmed.Dakrory
 *
 */
public interface IsalePaymentAppService {

	public List<salePayment> getAll();
	public salePayment addsalePayment(salePayment data);
	public salePayment getById(int id);
	public List<salePayment> getBySaleId(int saleId);
	public List<salePayment> getAllBetweenDateAndStatue(Calendar dateLower,Calendar dateHigher,int statue);
	public boolean delete(salePayment data);
}
