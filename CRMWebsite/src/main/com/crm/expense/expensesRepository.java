/**
 * 
 */
package main.com.crm.expense;

import java.util.Calendar;
import java.util.List;

/**
 * 
 * @author Ahmed.Dakrory
 *
 */
public interface expensesRepository {

	public List<expenses> getAll();
	public List<expenses> getAllForTypeBetweenDateAndRole(Calendar calLower,Calendar calHigher,int expensesType);
	public List<expenses> getAllExceptType(int type);
	public expenses addexpenses(expenses data);
	public expenses getById(int id);
	public boolean delete(expenses data) throws Exception;
}
