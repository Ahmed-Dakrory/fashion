/**
 * 
 */
package main.com.crm.productitem;

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
public class productitemRepositoryImpl implements productitemRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	

	@Override
	public productitem addproductitem(productitem data) {
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
	public List<productitem> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("productitem.getAll");

				 @SuppressWarnings("unchecked")
				List<productitem> results=query.list();
				 if(results.size()!=0){
					 return results;
				 }else{
					 return null;
				 }
	}

	
	@Override
	public boolean delete(productitem data)throws Exception {
		// TODO Auto-generated method stub
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(data);
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public productitem getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("productitem.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<productitem> results=query.list();
		 if(results.size()!=0){
			 return results.get(0);
		 }else{
			 return null;
		 }
	}

	@Override
	public List<productitem> getLastNProducts(int n) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("productitem.getLastNProducts");

		 query.setMaxResults(n);
		 @SuppressWarnings("unchecked")
		List<productitem> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}

	@Override
	public List<productitem> getitemsWithProductId(int idProduct) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("productitem.getByProductId").setInteger("product_id",idProduct);

		 @SuppressWarnings("unchecked")
		List<productitem> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}

	@Override
	public List<productitem> getAvailableItemsWithQuantity(int product_id,int n_items) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("productitem.getProductsAvailableItems").setInteger("product_id",product_id);
		 query.setMaxResults(n_items);
		 @SuppressWarnings("unchecked")
		List<productitem> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}

	@Override
	public List<productitem> getitemsWithSaleId(int idSale) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("productitem.getBySaleId").setInteger("sale_id",idSale);

		 @SuppressWarnings("unchecked")
		List<productitem> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}


}
