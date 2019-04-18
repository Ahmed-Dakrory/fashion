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
public interface productMaterialsRepository {

	public List<productMaterials> getAll();
	public productMaterials addproductMaterials(productMaterials data);
	public productMaterials getById(int id);
	public boolean delete(productMaterials data);
}
