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
import main.com.crm.loginNeeds.userAppServiceImpl;
import main.com.crm.moneyBox.moneybox;
import main.com.crm.moneyBox.moneyboxAppServiceImpl;


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
	

	@ManagedProperty(value = "#{moneyboxFacadeImpl}")
	private moneyboxAppServiceImpl moneyboxDataFacede; 
	
	private expenses selectedExpenses;
	
	
	

	private expenses addedExpenses;
	
	private String dateString;
	private int paymentAddingMethod;
	private int payedOrNot;

	

	@ManagedProperty(value = "#{userFacadeImpl}")
	private userAppServiceImpl userDataFacede; 
	private List<user> allUsers;
	
	
	
	
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
		allUsers=userDataFacede.getAllWithRole(user.ROLE_SHAREHOLDER);
		allUsers.addAll(userDataFacede.getAllWithRole(user.ROLE_ADMIN));
	}

	
	public void selectExpenses(int expensesId) {
		selectedExpenses=expensesDataFacede.getById(expensesId);
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/expenses/expenseDetails.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	 
	 public void deleteExpenses(int idExpenses) {
		 expenses expenses=expensesDataFacede.getById(idExpenses);
		 
		 try {
			expensesDataFacede.delete(expenses);
			moneybox mB=moneyboxDataFacede.getByUserId(expenses.getBoughtByUser_id().getId());
			if(expenses.getStatues()==1) {
				// he pay this amount so we need retrieve it
				if(expenses.getPayedOrAddToShares()==1) {
					//Payed directly
					mB.setMoneyRemains(mB.getMoneyRemains()+(expenses.getPricePerUnit()*expenses.getQuantity()));
					
				}else {
					//Payed as shares
					mB.setTotalMoney(mB.getTotalMoney()-(expenses.getPricePerUnit()*expenses.getQuantity()));
					
				}
			}else {
				// pay as payable
				mB.setTotalMoney(mB.getTotalMoney()-(expenses.getPricePerUnit()*expenses.getQuantity()));
				mB.setPayable(mB.getPayable()-(expenses.getPricePerUnit()*expenses.getQuantity()));
			}
			moneyboxDataFacede.addmoneybox(mB);
		} catch (Exception e) {
			PrimeFaces.current().executeScript("new PNotify({\r\n" + 
					"			title: 'Problem!',\r\n" + 
					"			text: 'Cannot delete this expenses, related to Assets or Raw material!',\r\n" + 
					"			type: 'error',\r\n" + 
					"			left:\"1%\"\r\n" + 
					"		});");
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
			   .getExternalContext().redirect("/pages/secured/admin/expenses/addExpense.jsf");
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
		addedExpenses.setType(0);
		addedExpenses.setAddedByUser_id(loginBean.getTheUserOfThisAccount());
		addedExpenses.setPayedOrAddToShares(paymentAddingMethod);
		
		
		
		moneybox mB=moneyboxDataFacede.getByUserId(addedExpenses.getBoughtByUser_id().getId());
		if(payedOrNot==1) {
		if(paymentAddingMethod==1) {
			//Billed method
			if(mB.getMoneyRemains()>(addedExpenses.getPricePerUnit()*addedExpenses.getQuantity())) {
				
			mB.setMoneyRemains(mB.getMoneyRemains()-(addedExpenses.getPricePerUnit()*addedExpenses.getQuantity()));
			expensesDataFacede.addexpenses(addedExpenses);
			moneyboxDataFacede.addmoneybox(mB);
			PrimeFaces.current().executeScript("new PNotify({\r\n" + 
					"			title: 'Success',\r\n" + 
					"			text: 'New expenses has been added.',\r\n" + 
					"			type: 'success'\r\n" + 
					"		});");
			
			try {
				FacesContext.getCurrentInstance()
				   .getExternalContext().redirect("/pages/secured/admin/expenses/expenses.jsf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}else {
				PrimeFaces.current().executeScript("new PNotify({\r\n" + 
						"			title: 'Problem!',\r\n" + 
						"			text: 'The user moneybox has no sufficient Money!',\r\n" + 
						"			type: 'error',\r\n" + 
						"			left:\"1%\"\r\n" + 
						"		});");
			}
		}else if(paymentAddingMethod==2) {
			//Add to Shared method
			mB.setTotalMoney(mB.getTotalMoney()+(addedExpenses.getPricePerUnit()*addedExpenses.getQuantity()));
			expensesDataFacede.addexpenses(addedExpenses);
			
			
			moneyboxDataFacede.addmoneybox(mB);
			PrimeFaces.current().executeScript("new PNotify({\r\n" + 
					"			title: 'Success',\r\n" + 
					"			text: 'New expenses has been added.',\r\n" + 
					"			type: 'success'\r\n" + 
					"		});");
			
			try {
				FacesContext.getCurrentInstance()
				   .getExternalContext().redirect("/pages/secured/admin/expenses/expenses.jsf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		}else if(payedOrNot==2){
			//Add to Payable method and to the total so the remains is stay the same
			mB.setPayable(mB.getPayable()+(addedExpenses.getPricePerUnit()*addedExpenses.getQuantity()));
			mB.setTotalMoney(mB.getTotalMoney()+(addedExpenses.getPricePerUnit()*addedExpenses.getQuantity()));
			expensesDataFacede.addexpenses(addedExpenses);
			
			
			moneyboxDataFacede.addmoneybox(mB);
			PrimeFaces.current().executeScript("new PNotify({\r\n" + 
					"			title: 'Success',\r\n" + 
					"			text: 'New expenses has been added.',\r\n" + 
					"			type: 'success'\r\n" + 
					"		});");
			
			try {
				FacesContext.getCurrentInstance()
				   .getExternalContext().redirect("/pages/secured/admin/expenses/expenses.jsf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	
	public moneyboxAppServiceImpl getMoneyboxDataFacede() {
		return moneyboxDataFacede;
	}

	public void setMoneyboxDataFacede(moneyboxAppServiceImpl moneyboxDataFacede) {
		this.moneyboxDataFacede = moneyboxDataFacede;
	}

	public List<user> getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(List<user> allUsers) {
		this.allUsers = allUsers;
	}

	public userAppServiceImpl getUserDataFacede() {
		return userDataFacede;
	}

	public void setUserDataFacede(userAppServiceImpl userDataFacede) {
		this.userDataFacede = userDataFacede;
	}
	
	
	
}
