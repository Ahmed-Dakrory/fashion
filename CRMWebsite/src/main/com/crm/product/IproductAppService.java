/**
 * 
 */
package main.com.crm.product;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface IproductAppService {

	public List<product> getAll();
	public List<product> getLastNProducts(int n);
	public product addproduct(product data);
	public product getById(int id);
	public boolean delete(product data)throws Exception;
}
