package main.com.crm.expenseBean;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import main.com.crm.expense.expenses;
import main.com.crm.expense.expensesAppServiceImpl;
import main.com.crm.loginNeeds.user;


@ManagedBean(name = "expensesBean")
@SessionScoped
public class expensesBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 655527256791420301L;

	private List<expenses> listOfExpenses;
	

	@ManagedProperty(value = "#{expensesFacadeImpl}")
	private expensesAppServiceImpl expensesDataFacede; 
	
	 
	@ManagedProperty(value = "#{loginBean}")
	private main.com.crm.loginNeeds.loginBean loginBean;
	
	
	private expenses selectedExpenses;
	
	
	

	private expenses addedExpenses;
	
	private String dateString;
	private int paymentAddingMethod;
	private int payedOrNot;
	
	
	
	
	@PostConstruct
	public void init() {
		addedExpenses=new expenses();
		addedExpenses.setAddedByUser_id(new user());
		addedExpenses.setBoughtByUser_id(new user());
		payedOrNot=1;
		refresh();
		
	}
	
	public void refresh(){
		listOfExpenses=expensesDataFacede.getAll();
	}

	
	public void selectExpenses(int expensesId) {
		selectedExpenses=expensesDataFacede.getById(expensesId);
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/expenses/expenseDetails.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	 
	     
	    
	     
	
	public void goToAddNewExpenses() {
		
		addedExpenses=new expenses();
		addedExpenses.setAddedByUser_id(new user());
		addedExpenses.setBoughtByUser_id(new user());
		paymentAddingMethod=1;
		payedOrNot=1;
		dateString=null;
		
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/expenses/addExpense.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addNewExpenses() {
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-dd-MM HH:mm:ss"); 
		try {
			if(dateString!=null) {
			Date date=formatter.parse(dateString);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			addedExpenses.setDate(cal);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		addedExpenses.setStatues(payedOrNot);
		addedExpenses.setAddedByUser_id(loginBean.getTheUserOfThisAccount());
		expensesDataFacede.addexpenses(addedExpenses);
		
		PrimeFaces.current().executeScript("new PNotify({\r\n" + 
				"			title: 'Success',\r\n" + 
				"			text: 'New expenses has been added.',\r\n" + 
				"			type: 'success'\r\n" + 
				"		});");
		
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/expenses/expenses.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<expenses> getListOfExpenses() {
		return listOfExpenses;
	}

	public void setListOfExpenses(List<expenses> listOfExpenses) {
		this.listOfExpenses = listOfExpenses;
	}

	public expensesAppServiceImpl getExpensesDataFacede() {
		return expensesDataFacede;
	}

	public void setExpensesDataFacede(expensesAppServiceImpl expensesDataFacede) {
		this.expensesDataFacede = expensesDataFacede;
	}

	public main.com.crm.loginNeeds.loginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(main.com.crm.loginNeeds.loginBean loginBean) {
		this.loginBean = loginBean;
	}

	public expenses getSelectedExpenses() {
		return selectedExpenses;
	}

	public void setSelectedExpenses(expenses selectedExpenses) {
		this.selectedExpenses = selectedExpenses;
	}

	public expenses getAddedExpenses() {
		return addedExpenses;
	}

	public void setAddedExpenses(expenses addedExpenses) {
		this.addedExpenses = addedExpenses;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public int getPaymentAddingMethod() {
		return paymentAddingMethod;
	}

	public void setPaymentAddingMethod(int paymentAddingMethod) {
		this.paymentAddingMethod = paymentAddingMethod;
	}

	public int getPayedOrNot() {
		return payedOrNot;
	}

	public void setPayedOrNot(int payedOrNot) {
		this.payedOrNot = payedOrNot;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	
}
