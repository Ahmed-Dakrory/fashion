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
public interface saleRepository {

	public List<sale> getAll();
	public sale addsale(sale data);
	public sale getById(int id);
	public boolean delete(sale data);
}
