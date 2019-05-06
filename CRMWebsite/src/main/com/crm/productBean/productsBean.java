package main.com.crm.productBean;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;

import main.com.crm.product.product;
import main.com.crm.product.productAppServiceImpl;
import main.com.crm.productMaterials.productMaterials;
import main.com.crm.productMaterials.productMaterialsAppServiceImpl;
import main.com.crm.productitem.productitem;
import main.com.crm.productitem.productitemAppServiceImpl;
import main.com.crm.rawMaterial.rawMaterial;
import main.com.crm.rawMaterial.rawMaterialAppServiceImpl;
import main.com.crm.sale.sale;
import main.com.crm.sale.saleAppServiceImpl;
import main.com.crm.salePayment.salePayment;
import main.com.crm.salePayment.salePaymentAppServiceImpl;
import main.com.crm.loginNeeds.user;


@ManagedBean(name = "productsBean")
@SessionScoped
public class productsBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 655527256791420301L;

	private List<product> listOfProducts;
	

	@ManagedProperty(value = "#{productFacadeImpl}")
	private productAppServiceImpl productDataFacede; 
	

	@ManagedProperty(value = "#{saleFacadeImpl}")
	private saleAppServiceImpl saleDataFacede; 
	
	@ManagedProperty(value = "#{salePaymentFacadeImpl}")
	private salePaymentAppServiceImpl salePaymentDataFacede; 
	

	@ManagedProperty(value = "#{rawMaterialFacadeImpl}")
	private rawMaterialAppServiceImpl rawMaterialDataFacede; 
	
	@ManagedProperty(value = "#{productMaterialsFacadeImpl}")
	private productMaterialsAppServiceImpl productMaterialsDataFacede; 
	
	@ManagedProperty(value = "#{productitemFacadeImpl}")
	private productitemAppServiceImpl productitemDataFacede; 
	 
	@ManagedProperty(value = "#{loginBean}")
	private main.com.crm.loginNeeds.loginBean loginBean;
	
	
	private product selectedProducts;
	private List<productMaterials> selectedProductMaterials;
	private List<productitem> selectedProductItems;
	
	
	
/*
 * The part of the added products
 */
	private product addedProducts;
	private List<productMaterials> listOfSelectedRawMaterials;
	private int selectedRawMaterialId;
	private int quantityOfRawMaterialsUsedByProduct;
	private Float salerawMaterialPricePerUnit;
	private int newOrReproduced;
	private int reProducedProductId;
	private product reProducedProduct;
	
	
	private String dateString;
	
	
	
	
	@PostConstruct
	public void init() {
		addedProducts=new product();
		addedProducts.setAddedByUser_id(new user());
		newOrReproduced=product.NEW_TYPE;
		refresh();
		
	}
	
	public void refresh(){
		listOfProducts=productDataFacede.getAll();
	}

	
	public void selectProducts(int productId) {
		selectedProducts=productDataFacede.getById(productId);
		selectedProductMaterials=productMaterialsDataFacede.getAllProductMaterialsWithProductId(selectedProducts.getId());
		selectedProductItems=productitemDataFacede.getitemsWithProductId(productId);
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/products/productDetails.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void returnProductItem(int itemId) {
		productitem pItem=productitemDataFacede.getById(itemId);
		sale saleForItem=saleDataFacede.getById(pItem.getSale_id().getId());
		product products=productDataFacede.getById(pItem.getProduct_id().getId());
		
		//return product quantity to product table
		products.setQuantityAvailable(products.getQuantityAvailable()+1);
		productDataFacede.addproduct(products);

		//return productItem to productitem  table
		pItem.setState(productitem.STATE_RETURN);
		productitemDataFacede.addproductitem(pItem);

		//return product quantity to sale table
		saleForItem.setPriceTotal(saleForItem.getPriceTotal()-pItem.getRecievedPriceForThisItem());
		saleForItem.setQuantity(saleForItem.getQuantity()-1);
		saleDataFacede.addsale(saleForItem);
		
		//add new details for the salePayment Table
		salePayment sPayment=new salePayment();
		sPayment.setAmount(-pItem.getRecievedPriceForThisItem());
		sPayment.setDateEnd(Calendar.getInstance());
		sPayment.setPayedOrNot(salePayment.RETURNS);
		sPayment.setSale_id(saleForItem);
		salePaymentDataFacede.addsalePayment(sPayment);
		
		
		
		
		//Update the data
		selectedProducts=productDataFacede.getById(products.getId());
		selectedProductMaterials=productMaterialsDataFacede.getAllProductMaterialsWithProductId(selectedProducts.getId());
		selectedProductItems=productitemDataFacede.getitemsWithProductId(products.getId());
		
	}
	 
	     
	public void deleteProduct(int id) {
		product deletedProduct=productDataFacede.getById(id);
		List<sale> allSales=saleDataFacede.getAllProductById(deletedProduct.getId());
		if(allSales==null) {
				List<productMaterials>pMs=productMaterialsDataFacede.getAllProductMaterialsWithProductId(id);
				if(pMs!=null) {
				for(int i=0;i<pMs.size();i++) {
					//Return the raw materials quantity used by this product
					rawMaterial rM=pMs.get(i).getRawMaterial_id();
					rM.setAvailableQuantity(rM.getAvailableQuantity()+pMs.get(i).getQuantityUsedByThisProduct());
					rawMaterialDataFacede.addrawMaterial(rM);
					
					productMaterialsDataFacede.delete(pMs.get(i));
				}
				}
				try {
					List<productitem> deletedProducts=productitemDataFacede.getitemsWithProductId(deletedProduct.getId());
					for(int i=0;i<deletedProducts.size();i++) {
						productitemDataFacede.delete(deletedProducts.get(i));
					}
					
					productDataFacede.delete(deletedProduct);
				} catch (Exception e) {
					PrimeFaces.current().executeScript("new PNotify({\r\n" + 
							"			title: 'Problem!',\r\n" + 
							"			text: 'Cannot delete this product, related to other data!',\r\n" + 
							"			type: 'error',\r\n" + 
							"			left:\"1%\"\r\n" + 
							"		});");
				}
			
		}else {
			PrimeFaces.current().executeScript("new PNotify({\r\n" + 
					"			title: 'Problem!',\r\n" + 
					"			text: 'Cannot delete this product, related to some payments!',\r\n" + 
					"			type: 'error',\r\n" + 
					"			left:\"1%\"\r\n" + 
					"		});");
		}
		
	}
	     
	
	public void goToAddNewProducts() {
		listOfSelectedRawMaterials=new ArrayList<productMaterials>();
		addedProducts=new product();
		addedProducts.setAddedByUser_id(new user());
		addedProducts.setRecommendedmarkUp((float) 0.0);
		dateString=null;
		newOrReproduced=product.NEW_TYPE;
		
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/products/addProduct.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void goToAddReadyMadeProducts() {
		listOfSelectedRawMaterials=new ArrayList<productMaterials>();
		addedProducts=new product();
		addedProducts.setAddedByUser_id(new user());
		addedProducts.setRecommendedmarkUp((float) 0.0);
		dateString=null;
		newOrReproduced=product.READYMADE_TYPE;

		
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/products/addProductReadyMade.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addNewMaterial() {
		System.out.println("Dakrory: "+selectedRawMaterialId+"quantity: "+quantityOfRawMaterialsUsedByProduct);
		rawMaterial addedMaterial=rawMaterialDataFacede.getById(selectedRawMaterialId);
		if(addedMaterial.getAvailableQuantity()<quantityOfRawMaterialsUsedByProduct) {
			PrimeFaces.current().executeScript("new PNotify({\r\n" + 
					"			title: 'Problem!',\r\n" + 
					"			text: 'Number of raw materials not available!',\r\n" + 
					"			type: 'error',\r\n" + 
					"			left:\"1%\"\r\n" + 
					"		});");
		}else if(quantityOfRawMaterialsUsedByProduct<=0){
			PrimeFaces.current().executeScript("new PNotify({\r\n" + 
					"			title: 'Problem!',\r\n" + 
					"			text: 'Enter a valid number!',\r\n" + 
					"			type: 'error',\r\n" + 
					"			left:\"1%\"\r\n" + 
					"		});");
		}else {
			productMaterials pM=new productMaterials();
			pM.setRawMaterial_id(addedMaterial);
			pM.setQuantityUsedByThisProduct(quantityOfRawMaterialsUsedByProduct);
			
			listOfSelectedRawMaterials.add(pM);
			selectedRawMaterialId=0;
			quantityOfRawMaterialsUsedByProduct=0;
			updateThetotalCostOfRawMaterials();
		}
		
		
	}
	
	public void updateThetotalCostOfRawMaterials() {
		Float totalPrice=(float) 0;
		for(int i=0;i<listOfSelectedRawMaterials.size();i++) {
			Float price_one_item_of_rawMaterial=listOfSelectedRawMaterials.get(i).getRawMaterial_id().getExpenses_id().getPricePerUnit();
			Float priceForItem=listOfSelectedRawMaterials.get(i).getQuantityUsedByThisProduct()*price_one_item_of_rawMaterial;
			totalPrice+=priceForItem;
		}
		addedProducts.setRawMaterialCostTotal(totalPrice);
		salerawMaterialPricePerUnit=addedProducts.getRawMaterialCostTotal()/addedProducts.getQuantity();
	}

	public void deleteRawMaterial(int index) {
		listOfSelectedRawMaterials.remove(index);
		updateThetotalCostOfRawMaterials();
	}
	

	public void printCommand() {
		System.out.println("Ahmed Dakrory: "+addedProducts.getName());
		System.out.println("Ahmed Dakrory: "+addedProducts.getQuantity());
		System.out.println("Ahmed Dakrory: "+addedProducts.getSize());
		System.out.println("Ahmed Dakrory: "+addedProducts.getProducedBy());
		System.out.println("Ahmed Dakrory: "+dateString);
		System.out.println("Ahmed Dakrory: "+addedProducts.getRecommendedmarkUp());
		System.out.println("Ahmed Dakrory: "+addedProducts.getRecommendedSalePrice());
	}
	
	
	public void addNewProducts() {
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-dd-MM HH:mm:ss"); 
		try {
			if(dateString!=null) {
			Date date=formatter.parse(dateString);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			addedProducts.setDate(cal);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		
		addedProducts.setAddedByUser_id(loginBean.getTheUserOfThisAccount());
		addedProducts.setQuantityAvailable(addedProducts.getQuantity());
		addedProducts.setNewOrReproduced(newOrReproduced);
		
		if(newOrReproduced==product.REPRODUCED_TYPE) {
			//Mean that it is reproduced
			reProducedProduct=productDataFacede.getById(reProducedProductId);
			if(!reProducedProduct.getDescription().isEmpty()) {
				addedProducts.setDescription(reProducedProduct.getDescription());
			}
			addedProducts.setForGender(reProducedProduct.getForGender());
			addedProducts.setName(reProducedProduct.getName());
			addedProducts.setSize(reProducedProduct.getSize());
			
			}
		
		if(newOrReproduced==product.READYMADE_TYPE) {
			addedProducts.setRawMaterialCostTotal(salerawMaterialPricePerUnit*addedProducts.getQuantity());
		}
		
		productDataFacede.addproduct(addedProducts);
		for(int i=0;i<addedProducts.getQuantity();i++) {
			productitem pItem=new productitem();
			pItem.setDate(Calendar.getInstance());
			pItem.setProduct_id(addedProducts);
			pItem.setState(productitem.STATE_PRODUCED);
			pItem.setRecievedPriceForThisItem((float) 0);
			productitemDataFacede.addproductitem(pItem);
		}
		addAllMaterialsToProduct();
		PrimeFaces.current().executeScript("new PNotify({\r\n" + 
				"			title: 'Success',\r\n" + 
				"			text: 'New product has been added.',\r\n" + 
				"			type: 'success'\r\n" + 
				"		});");
		
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/products/products.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addAllMaterialsToProduct() {
		for(int i=0;i<listOfSelectedRawMaterials.size();i++) {
			productMaterials pM=listOfSelectedRawMaterials.get(i);
			
			rawMaterial rM= rawMaterialDataFacede.getById(pM.getRawMaterial_id().getId());
			rM.setAvailableQuantity(rM.getAvailableQuantity()-pM.getQuantityUsedByThisProduct());
			rawMaterialDataFacede.addrawMaterial(rM);
			
			pM.setProduct_id(addedProducts);
			pM.setRawMaterial_id(rM);
			productMaterialsDataFacede.addproductMaterials(pM);
		}
		
	}

	public void handleImageUpload(FileUploadEvent event) {

		addedProducts.setImage(event.getFile().getContents());
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	
	public List<product> getListOfProducts() {
		return listOfProducts;
	}

	public void setListOfProducts(List<product> listOfProducts) {
		this.listOfProducts = listOfProducts;
	}

	public productAppServiceImpl getProductDataFacede() {
		return productDataFacede;
	}

	public void setProductDataFacede(productAppServiceImpl productDataFacede) {
		this.productDataFacede = productDataFacede;
	}

	public main.com.crm.loginNeeds.loginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(main.com.crm.loginNeeds.loginBean loginBean) {
		this.loginBean = loginBean;
	}

	public product getSelectedProducts() {
		return selectedProducts;
	}

	public void setSelectedProducts(product selectedProducts) {
		this.selectedProducts = selectedProducts;
	}

	public product getAddedProducts() {
		return addedProducts;
	}

	public void setAddedProducts(product addedProducts) {
		this.addedProducts = addedProducts;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public rawMaterialAppServiceImpl getRawMaterialDataFacede() {
		return rawMaterialDataFacede;
	}

	public void setRawMaterialDataFacede(rawMaterialAppServiceImpl rawMaterialDataFacede) {
		this.rawMaterialDataFacede = rawMaterialDataFacede;
	}

	public List<productMaterials> getListOfSelectedRawMaterials() {
		return listOfSelectedRawMaterials;
	}

	public void setListOfSelectedRawMaterials(List<productMaterials> listOfSelectedRawMaterials) {
		this.listOfSelectedRawMaterials = listOfSelectedRawMaterials;
	}

	public int getSelectedRawMaterialId() {
		return selectedRawMaterialId;
	}

	public void setSelectedRawMaterialId(int selectedRawMaterialId) {
		this.selectedRawMaterialId = selectedRawMaterialId;
	}

	public int getQuantityOfRawMaterialsUsedByProduct() {
		return quantityOfRawMaterialsUsedByProduct;
	}

	public void setQuantityOfRawMaterialsUsedByProduct(int quantityOfRawMaterialsUsedByProduct) {
		this.quantityOfRawMaterialsUsedByProduct = quantityOfRawMaterialsUsedByProduct;
	}

	public Float getSalerawMaterialPricePerUnit() {
		return salerawMaterialPricePerUnit;
	}

	public void setSalerawMaterialPricePerUnit(Float salerawMaterialPricePerUnit) {
		this.salerawMaterialPricePerUnit = salerawMaterialPricePerUnit;
	}

	public int getNewOrReproduced() {
		return newOrReproduced;
	}

	public void setNewOrReproduced(int newOrReproduced) {
		this.newOrReproduced = newOrReproduced;
	}

	public product getReProducedProduct() {
		return reProducedProduct;
	}

	public void setReProducedProduct(product reProducedProduct) {
		this.reProducedProduct = reProducedProduct;
	}

	public int getReProducedProductId() {
		return reProducedProductId;
	}

	public void setReProducedProductId(int reProducedProductId) {
		this.reProducedProductId = reProducedProductId;
	}

	public productMaterialsAppServiceImpl getProductMaterialsDataFacede() {
		return productMaterialsDataFacede;
	}

	public void setProductMaterialsDataFacede(productMaterialsAppServiceImpl productMaterialsDataFacede) {
		this.productMaterialsDataFacede = productMaterialsDataFacede;
	}

	public saleAppServiceImpl getSaleDataFacede() {
		return saleDataFacede;
	}

	public void setSaleDataFacede(saleAppServiceImpl saleDataFacede) {
		this.saleDataFacede = saleDataFacede;
	}

	public List<productMaterials> getSelectedProductMaterials() {
		return selectedProductMaterials;
	}

	public void setSelectedProductMaterials(List<productMaterials> selectedProductMaterials) {
		this.selectedProductMaterials = selectedProductMaterials;
	}

	public productitemAppServiceImpl getProductitemDataFacede() {
		return productitemDataFacede;
	}

	public void setProductitemDataFacede(productitemAppServiceImpl productitemDataFacede) {
		this.productitemDataFacede = productitemDataFacede;
	}

	public List<productitem> getSelectedProductItems() {
		return selectedProductItems;
	}

	public void setSelectedProductItems(List<productitem> selectedProductItems) {
		this.selectedProductItems = selectedProductItems;
	}

	public salePaymentAppServiceImpl getSalePaymentDataFacede() {
		return salePaymentDataFacede;
	}

	public void setSalePaymentDataFacede(salePaymentAppServiceImpl salePaymentDataFacede) {
		this.salePaymentDataFacede = salePaymentDataFacede;
	}

	
	
	
	
}
