/**
 * 
 */
package main.com.crm.salePayment;

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
	public boolean delete(salePayment data);
}
