/**
 * 
 */
package main.com.crm.productMaterials;

import java.util.List;

/**
 * 
 * @author Ahmed.Dakrory
 *
 */
public interface IproductMaterialsAppService {

	public List<productMaterials> getAll();
	public List<productMaterials> getAllProductMaterialsWithProductId(int id);
	public productMaterials addproductMaterials(productMaterials data);
	public productMaterials getById(int id);
	public boolean delete(productMaterials data);
}
