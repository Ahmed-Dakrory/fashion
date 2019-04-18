/**
 * 
 */
package main.com.crm.expense;

import java.util.List;

/**
 * 
 * @author Ahmed.Dakrory
 *
 */
public interface expensesRepository {

	public List<expenses> getAll();
	public expenses addexpenses(expenses data);
	public expenses getById(int id);
	public boolean delete(expenses data);
}
