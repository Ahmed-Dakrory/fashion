/**
 * 
 */
package main.com.crm.moneyBox;

import java.util.List;

/**
 * 
 * @author Ahmed.Dakrory
 *
 */
public interface ImoneyboxAppService {

	public List<moneybox> getAll();
	public moneybox addmoneybox(moneybox data);
	public moneybox getById(int id);
	public boolean delete(moneybox data);
}
