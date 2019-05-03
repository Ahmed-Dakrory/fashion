/**
 * 
 */
package main.com.crm.productMaterials;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author A7med Al-Dakrory
 *
 */
@Repository
@Transactional
public class productMaterialsRepositoryImpl implements productMaterialsRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	

	@Override
	public productMaterials addproductMaterials(productMaterials data) {
		try{
			data.setLastUpdate(Calendar.getInstance());
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.saveOrUpdate(data);
			tx1.commit();
			session.close(); 
			return data; 
			}
			catch(Exception ex)
			{
				System.out.println(">>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<productMaterials> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("productMaterials.getAll");

				 @SuppressWarnings("unchecked")
				List<productMaterials> results=query.list();
				 if(results.size()!=0){
					 return results;
				 }else{
					 return null;
				 }
	}

	
	@Override
	public boolean delete(productMaterials data) {
		// TODO Auto-generated method stub
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(data);
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public productMaterials getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("productMaterials.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<productMaterials> results=query.list();
		 if(results.size()!=0){
			 return results.get(0);
		 }else{
			 return null;
		 }
	}

	@Override
	public List<productMaterials> getAllProductMaterialsWithProductId(int id) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("productMaterials.getByProductId").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<productMaterials> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}
	

}
