/**
 * 
 */
package main.com.crm.customer;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface IcustomerAppService {

	public List<customer> getAll();
	public customer addcustomer(customer data);
	public customer getById(int id);
	public boolean delete(customer data);
}
