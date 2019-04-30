package main.com.crm.generalBean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;

import main.com.crm.expense.expenses;
import main.com.crm.expense.expensesAppServiceImpl;
import main.com.crm.loginNeeds.user;
import main.com.crm.loginNeeds.userAppServiceImpl;
import main.com.crm.moneyBox.moneybox;
import main.com.crm.moneyBox.moneyboxAppServiceImpl;
import main.com.crm.product.product;
import main.com.crm.product.productAppServiceImpl;
import main.com.crm.salePayment.salePayment;
import main.com.crm.salePayment.salePaymentAppServiceImpl;


@ManagedBean(name = "generalBean")
@SessionScoped
public class generalBean implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7383039904260474699L;


	/**
	 * 
	 */
	
	@ManagedProperty(value = "#{loginBean}")
	private main.com.crm.loginNeeds.loginBean loginBean;
	
	

	@ManagedProperty(value = "#{userFacadeImpl}")
	private userAppServiceImpl userDataFacede; 
	

	@ManagedProperty(value = "#{expensesFacadeImpl}")
	private expensesAppServiceImpl expensesDataFacede; 

	@ManagedProperty(value = "#{moneyboxFacadeImpl}")
	private moneyboxAppServiceImpl moneyboxDataFacede; 
	

	@ManagedProperty(value = "#{salePaymentFacadeImpl}")
	private salePaymentAppServiceImpl salePaymentDataFacede; 
	

	@ManagedProperty(value = "#{productFacadeImpl}")
	private productAppServiceImpl productDataFacede; 
	
	private List<product> newProducts;
	
	private user selectedUser;
	
	private List<user> allUsers;
	private List<moneybox> allmoneybox;
	private List<expenses> allexpensesBetweenDatesForRawMaterial;
	private List<expenses> allexpensesBetweenDatesForOthers;
	private List<expenses> allexpensesBetweenDates;
	private List<salePayment> allsalePaymentsBetweenDatesPayed;
	
	
	private List<expenses> allexpensesBetweenDatesForRawMaterialNow;
	private List<expenses> allexpensesBetweenDatesForOthersNow;
	private List<expenses> allexpensesBetweenDatesNow;
	private List<salePayment> allsalePaymentsBetweenDatesPayedNow;
	
	
	private moneybox myMoneyBox;
	private Float percentageRemainingMoney;
	private Float percentageMyProfitThisMonth;
	private Float percentageMyProfitOverSelected;
	
	private String word;
	

	private String stringDateLower;
	private String stringDateHigher;

	private Float totalProfitThisMonth;
	private Float totalPaymentsThisMonth;
	private Float totalExpensesThisMonth;
	
	private Float totalProfitChoosen;
	private Float totalPaymentsChoosen;
	private Float totalExpensesChoosen;
	
	@PostConstruct
	public void init() {
		

		
		

		refresh();
	}
	
	public void refresh(){
		newProducts=productDataFacede.getLastNProducts(6);
		System.out.println("Ahmed Dakrory: "+newProducts.size());
		allUsers=userDataFacede.getAll();
		allmoneybox=moneyboxDataFacede.getAll();
		if(loginBean.getTheUserOfThisAccount().getId()!=null) {
		myMoneyBox=moneyboxDataFacede.getByUserId(loginBean.getTheUserOfThisAccount().getId());
		percentageRemainingMoney=(myMoneyBox.getTotalMoney()-myMoneyBox.getMoneyRemains())/myMoneyBox.getTotalMoney()*100;

		}

		updateProfitOfThisMonth();		

		if(loginBean.getTheUserOfThisAccount().getId()!=null) {
		percentageMyProfitThisMonth=updateMyPercentOfProfit(totalProfitThisMonth);
		}
	}

	private Float updateMyPercentOfProfit(Float totalProfitThisMonth2) {

		Float myMoney=myMoneyBox.getTotalMoney();
		Float totalMoney=(float) 0;
		for(int i=0;i<allmoneybox.size();i++) {
			totalMoney+=allmoneybox.get(i).getTotalMoney();
		}
		return (myMoney/totalMoney)*totalProfitThisMonth2;
	}

	public void getTheRangeOfDates() {
		totalExpensesChoosen=(float) 0;
		totalPaymentsChoosen=(float) 0;
		totalProfitChoosen=(float) 0;
		allexpensesBetweenDatesForOthers=new ArrayList<expenses>();
		allexpensesBetweenDatesForRawMaterial=new ArrayList<expenses>();
		allexpensesBetweenDates=new ArrayList<expenses>();
		allsalePaymentsBetweenDatesPayed=new ArrayList<salePayment>();
		
		
		Calendar calLower = Calendar.getInstance();
		Calendar calHigher = Calendar.getInstance();
		
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-dd-MM HH:mm:ss"); 
		try {
			if(stringDateLower!=null) {
			Date date=formatter.parse(stringDateLower);
			calLower.setTime(date);

			System.out.println(calLower.getTime().toString());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		try {
			if(stringDateHigher!=null) {
			Date date=formatter.parse(stringDateHigher);
			calHigher.setTime(date);

			System.out.println(calHigher.getTime().toString());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		allexpensesBetweenDatesForRawMaterial=expensesDataFacede.getAllForTypeBetweenDateAndRole(calLower, calHigher, expenses.TYPE_RAW_MATERIAL);
		allexpensesBetweenDatesForOthers=expensesDataFacede.getAllForTypeBetweenDateAndRole(calLower, calHigher, expenses.TYPE_NORMAL_EXPENSES);
		if(allexpensesBetweenDatesForOthers!=null) {
			for(int j=0;j<allexpensesBetweenDatesForOthers.size();j++) {
				allexpensesBetweenDates.add(allexpensesBetweenDatesForOthers.get(j));
			}
		}
		if(allexpensesBetweenDatesForRawMaterial!=null) {
			for(int j=0;j<allexpensesBetweenDatesForRawMaterial.size();j++) {
				allexpensesBetweenDates.add(allexpensesBetweenDatesForRawMaterial.get(j));
			}
		}
		
		allsalePaymentsBetweenDatesPayed=salePaymentDataFacede.getAllBetweenDateAndStatue(calLower, calHigher, salePayment.PAYED);
	
		totalExpensesChoosen=(float)0;
		
		if(allexpensesBetweenDates!=null) {
			for(int i=0;i<allexpensesBetweenDates.size();i++) {
				totalExpensesChoosen+=allexpensesBetweenDates.get(i).getPricePerUnit()*allexpensesBetweenDates.get(i).getQuantity();
			}
		}
		
		
		totalProfitChoosen=(float)0;
		
		if(allsalePaymentsBetweenDatesPayed!=null) {
			for(int i=0;i<allsalePaymentsBetweenDatesPayed.size();i++) {
				totalPaymentsChoosen+=allsalePaymentsBetweenDatesPayed.get(i).getAmount();
			}
		}
		totalProfitChoosen=totalPaymentsChoosen-totalExpensesChoosen;

		percentageMyProfitOverSelected=updateMyPercentOfProfit(totalProfitChoosen);
	}

	
	public void updateProfitOfThisMonth() {

		totalExpensesThisMonth=(float) 0;
		totalPaymentsThisMonth=(float) 0;
		totalProfitThisMonth=(float) 0;
		allexpensesBetweenDatesForOthersNow=new ArrayList<expenses>();
		allexpensesBetweenDatesForRawMaterialNow=new ArrayList<expenses>();
		allexpensesBetweenDatesNow=new ArrayList<expenses>();
		allsalePaymentsBetweenDatesPayedNow=new ArrayList<salePayment>();
		
		Calendar calLower = Calendar.getInstance();
		Calendar calHigher = Calendar.getInstance();

		calLower.set(Calendar.DAY_OF_MONTH, 1);
		calHigher.set(Calendar.DAY_OF_MONTH, 30);
		
		allexpensesBetweenDatesForRawMaterialNow=expensesDataFacede.getAllForTypeBetweenDateAndRole(calLower, calHigher, expenses.TYPE_RAW_MATERIAL);
		allexpensesBetweenDatesForOthersNow=expensesDataFacede.getAllForTypeBetweenDateAndRole(calLower, calHigher, expenses.TYPE_NORMAL_EXPENSES);
		if(allexpensesBetweenDatesForOthersNow!=null) {
			for(int j=0;j<allexpensesBetweenDatesForOthersNow.size();j++) {
				allexpensesBetweenDatesNow.add(allexpensesBetweenDatesForOthersNow.get(j));
			}
		}
		
		if(allexpensesBetweenDatesForRawMaterialNow!=null) {
			for(int j=0;j<allexpensesBetweenDatesForRawMaterialNow.size();j++) {
				allexpensesBetweenDatesNow.add(allexpensesBetweenDatesForRawMaterialNow.get(j));
			}
		}
		
		allsalePaymentsBetweenDatesPayedNow=salePaymentDataFacede.getAllBetweenDateAndStatue(calLower, calHigher, salePayment.PAYED);
		totalExpensesThisMonth=(float)0;
		if(allexpensesBetweenDatesNow!=null) {
			for(int i=0;i<allexpensesBetweenDatesNow.size();i++) {
				totalExpensesThisMonth+=allexpensesBetweenDatesNow.get(i).getPricePerUnit()*allexpensesBetweenDatesNow.get(i).getQuantity();
			}
		}
		totalPaymentsThisMonth=(float)0;
		
		if(allsalePaymentsBetweenDatesPayedNow!=null) {
			for(int i=0;i<allsalePaymentsBetweenDatesPayedNow.size();i++) {
				totalPaymentsThisMonth+=allsalePaymentsBetweenDatesPayedNow.get(i).getAmount();
			}
		}
		totalProfitThisMonth=totalPaymentsThisMonth-totalExpensesThisMonth;
		
		
	}

	

	public void showUser(int idUser) {
		selectedUser=userDataFacede.getById(idUser);
		Map<String,Object> options = new HashMap<String, Object>();
	    options.put("modal", true);
        options.put("width", 375);
        options.put("height", 550);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("background", "#00ffff00");
	    PrimeFaces.current().dialog().openDynamic("/pages/secured/admin/general/userDetails", options, null);
		
	}

	
	public void calcAllUserParameters() {
		
	}
	public void printCommand() {
		System.out.println("Ahmed Dakrory");
		System.out.println("Ahmed Dakrory: "+word);
	}
	public void runjavascript() {
		PrimeFaces.current().executeScript("play();");
	}
	public void doSomethingAndCloseDialog1() {
		
		RequestContext.getCurrentInstance().closeDialog("/pages/secured/admin/general/userDetails");
    }
	
	public main.com.crm.loginNeeds.loginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(main.com.crm.loginNeeds.loginBean loginBean) {
		this.loginBean = loginBean;
	}

	public userAppServiceImpl getUserDataFacede() {
		return userDataFacede;
	}

	public void setUserDataFacede(userAppServiceImpl userDataFacede) {
		this.userDataFacede = userDataFacede;
	}

	public user getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(user selectedUser) {
		this.selectedUser = selectedUser;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<user> getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(List<user> allUsers) {
		this.allUsers = allUsers;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public moneyboxAppServiceImpl getMoneyboxDataFacede() {
		return moneyboxDataFacede;
	}

	public void setMoneyboxDataFacede(moneyboxAppServiceImpl moneyboxDataFacede) {
		this.moneyboxDataFacede = moneyboxDataFacede;
	}

	public List<moneybox> getAllmoneybox() {
		return allmoneybox;
	}

	public void setAllmoneybox(List<moneybox> allmoneybox) {
		this.allmoneybox = allmoneybox;
	}

	public moneybox getMyMoneyBox() {
		return myMoneyBox;
	}

	public void setMyMoneyBox(moneybox myMoneyBox) {
		this.myMoneyBox = myMoneyBox;
	}

	public Float getPercentageRemainingMoney() {
		return percentageRemainingMoney;
	}

	public void setPercentageRemainingMoney(Float percentageRemainingMoney) {
		this.percentageRemainingMoney = percentageRemainingMoney;
	}

	public expensesAppServiceImpl getExpensesDataFacede() {
		return expensesDataFacede;
	}

	public void setExpensesDataFacede(expensesAppServiceImpl expensesDataFacede) {
		this.expensesDataFacede = expensesDataFacede;
	}

	public List<expenses> getAllexpensesBetweenDatesForRawMaterial() {
		return allexpensesBetweenDatesForRawMaterial;
	}

	public void setAllexpensesBetweenDatesForRawMaterial(List<expenses> allexpensesBetweenDatesForRawMaterial) {
		this.allexpensesBetweenDatesForRawMaterial = allexpensesBetweenDatesForRawMaterial;
	}

	public List<expenses> getAllexpensesBetweenDatesForOthers() {
		return allexpensesBetweenDatesForOthers;
	}

	public void setAllexpensesBetweenDatesForOthers(List<expenses> allexpensesBetweenDatesForOthers) {
		this.allexpensesBetweenDatesForOthers = allexpensesBetweenDatesForOthers;
	}

	public String getStringDateLower() {
		return stringDateLower;
	}

	public void setStringDateLower(String stringDateLower) {
		this.stringDateLower = stringDateLower;
	}

	public String getStringDateHigher() {
		return stringDateHigher;
	}

	public void setStringDateHigher(String stringDateHigher) {
		this.stringDateHigher = stringDateHigher;
	}

	public salePaymentAppServiceImpl getSalePaymentDataFacede() {
		return salePaymentDataFacede;
	}

	public void setSalePaymentDataFacede(salePaymentAppServiceImpl salePaymentDataFacede) {
		this.salePaymentDataFacede = salePaymentDataFacede;
	}

	public List<salePayment> getAllsalePaymentsBetweenDatesPayed() {
		return allsalePaymentsBetweenDatesPayed;
	}

	public void setAllsalePaymentsBetweenDatesPayed(List<salePayment> allsalePaymentsBetweenDatesPayed) {
		this.allsalePaymentsBetweenDatesPayed = allsalePaymentsBetweenDatesPayed;
	}

	public List<expenses> getAllexpensesBetweenDates() {
		return allexpensesBetweenDates;
	}

	public void setAllexpensesBetweenDates(List<expenses> allexpensesBetweenDates) {
		this.allexpensesBetweenDates = allexpensesBetweenDates;
	}

	public List<expenses> getAllexpensesBetweenDatesForRawMaterialNow() {
		return allexpensesBetweenDatesForRawMaterialNow;
	}

	public void setAllexpensesBetweenDatesForRawMaterialNow(List<expenses> allexpensesBetweenDatesForRawMaterialNow) {
		this.allexpensesBetweenDatesForRawMaterialNow = allexpensesBetweenDatesForRawMaterialNow;
	}

	public List<expenses> getAllexpensesBetweenDatesForOthersNow() {
		return allexpensesBetweenDatesForOthersNow;
	}

	public void setAllexpensesBetweenDatesForOthersNow(List<expenses> allexpensesBetweenDatesForOthersNow) {
		this.allexpensesBetweenDatesForOthersNow = allexpensesBetweenDatesForOthersNow;
	}

	public List<expenses> getAllexpensesBetweenDatesNow() {
		return allexpensesBetweenDatesNow;
	}

	public void setAllexpensesBetweenDatesNow(List<expenses> allexpensesBetweenDatesNow) {
		this.allexpensesBetweenDatesNow = allexpensesBetweenDatesNow;
	}

	public List<salePayment> getAllsalePaymentsBetweenDatesPayedNow() {
		return allsalePaymentsBetweenDatesPayedNow;
	}

	public void setAllsalePaymentsBetweenDatesPayedNow(List<salePayment> allsalePaymentsBetweenDatesPayedNow) {
		this.allsalePaymentsBetweenDatesPayedNow = allsalePaymentsBetweenDatesPayedNow;
	}

	public Float getTotalProfitThisMonth() {
		return totalProfitThisMonth;
	}

	public void setTotalProfitThisMonth(Float totalProfitThisMonth) {
		this.totalProfitThisMonth = totalProfitThisMonth;
	}

	public Float getTotalPaymentsThisMonth() {
		return totalPaymentsThisMonth;
	}

	public void setTotalPaymentsThisMonth(Float totalPaymentsThisMonth) {
		this.totalPaymentsThisMonth = totalPaymentsThisMonth;
	}

	public Float getTotalExpensesThisMonth() {
		return totalExpensesThisMonth;
	}

	public void setTotalExpensesThisMonth(Float totalExpensesThisMonth) {
		this.totalExpensesThisMonth = totalExpensesThisMonth;
	}

	public Float getTotalProfitChoosen() {
		return totalProfitChoosen;
	}

	public void setTotalProfitChoosen(Float totalProfitChoosen) {
		this.totalProfitChoosen = totalProfitChoosen;
	}

	public Float getTotalPaymentsChoosen() {
		return totalPaymentsChoosen;
	}

	public void setTotalPaymentsChoosen(Float totalPaymentsChoosen) {
		this.totalPaymentsChoosen = totalPaymentsChoosen;
	}

	public Float getTotalExpensesChoosen() {
		return totalExpensesChoosen;
	}

	public void setTotalExpensesChoosen(Float totalExpensesChoosen) {
		this.totalExpensesChoosen = totalExpensesChoosen;
	}

	public Float getPercentageMyProfitThisMonth() {
		return percentageMyProfitThisMonth;
	}

	public void setPercentageMyProfitThisMonth(Float percentageMyProfitThisMonth) {
		this.percentageMyProfitThisMonth = percentageMyProfitThisMonth;
	}

	public Float getPercentageMyProfitOverSelected() {
		return percentageMyProfitOverSelected;
	}

	public void setPercentageMyProfitOverSelected(Float percentageMyProfitOverSelected) {
		this.percentageMyProfitOverSelected = percentageMyProfitOverSelected;
	}

	public productAppServiceImpl getProductDataFacede() {
		return productDataFacede;
	}

	public void setProductDataFacede(productAppServiceImpl productDataFacede) {
		this.productDataFacede = productDataFacede;
	}

	public List<product> getNewProducts() {
		return newProducts;
	}

	public void setNewProducts(List<product> newProducts) {
		this.newProducts = newProducts;
	}

	
	
	
	
}
