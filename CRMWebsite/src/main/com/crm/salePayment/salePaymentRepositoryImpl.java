/**
 * 
 */
package main.com.crm.salePayment;

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
public class salePaymentRepositoryImpl implements salePaymentRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	

	@Override
	public salePayment addsalePayment(salePayment data) {
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
	public List<salePayment> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("salePayment.getAll");

				 @SuppressWarnings("unchecked")
				List<salePayment> results=query.list();
				 if(results.size()!=0){
					 return results;
				 }else{
					 return null;
				 }
	}

	
	@Override
	public boolean delete(salePayment data) {
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
	public salePayment getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("salePayment.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<salePayment> results=query.list();
		 if(results.size()!=0){
			 return results.get(0);
		 }else{
			 return null;
		 }
	}

	@Override
	public List<salePayment> getBySaleId(int saleId) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("salePayment.getBySaleId").setInteger("id",saleId);

		 @SuppressWarnings("unchecked")
		List<salePayment> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}

	@Override
	public List<salePayment> getAllBetweenDateAndStatue(Calendar dateLower, Calendar dateHigher, int statue) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("salePayment.getAllBetweenDateAndStatue")
				 .setCalendar("dateLower", dateLower).setCalendar("dateHigher", dateHigher).setInteger("statue", statue);

		 @SuppressWarnings("unchecked")
		List<salePayment> results=query.list();
		 if(results.size()!=0){
			 return results;
		 }else{
			 return null;
		 }
	}
	

}
