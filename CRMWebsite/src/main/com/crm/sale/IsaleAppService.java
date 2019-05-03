/**
 * 
 */
package main.com.crm.sale;

import java.util.List;

/**
 * 
 * @author Ahmed.Dakrory
 *
 */
public interface IsaleAppService {

	public List<sale> getAll();
	public List<sale> getAllProductById(int idProduct);
	public sale addsale(sale data);
	public sale getById(int id);
	public boolean delete(sale data);
}
