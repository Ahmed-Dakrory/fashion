/**
 * 
 */
package main.com.crm.productitem;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface IproductitemAppService {

	public List<productitem> getAll();
	public List<productitem> getitemsWithProductId(int idProduct);
	public List<productitem> getitemsWithSaleId(int idSale);
	public List<productitem> getAvailableItemsWithQuantity(int product_id,int n_items);
	public List<productitem> getLastNProducts(int n);
	public productitem addproductitem(productitem data);
	public productitem getById(int id);
	public boolean delete(productitem data)throws Exception;
}
