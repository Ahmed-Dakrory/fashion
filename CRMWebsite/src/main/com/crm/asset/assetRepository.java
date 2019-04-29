/**
 * 
 */
package main.com.crm.asset;

import java.util.List;

/**
 * 
 * @author Ahmed.Dakrory
 *
 */
public interface assetRepository {

	public List<asset> getAll();
	public asset addasset(asset data);
	public asset getById(int id);
	public boolean delete(asset data)throws Exception;
}
