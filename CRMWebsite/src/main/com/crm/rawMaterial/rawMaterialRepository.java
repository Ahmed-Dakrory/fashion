/**
 * 
 */
package main.com.crm.rawMaterial;

import java.util.List;

/**
 * 
 * @author Ahmed.Dakrory
 *
 */
public interface rawMaterialRepository {

	public List<rawMaterial> getAll();
	public rawMaterial addrawMaterial(rawMaterial data);
	public rawMaterial getById(int id);
	public boolean delete(rawMaterial data);
}
