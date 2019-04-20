package main.com.crm.assetBean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import main.com.crm.asset.asset;
import main.com.crm.asset.assetAppServiceImpl;


@ManagedBean(name = "assetsBean")
@SessionScoped
public class assetsBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 655527256791420301L;

	private List<asset> listOfAssets;
	

	@ManagedProperty(value = "#{assetFacadeImpl}")
	private assetAppServiceImpl assetDataFacede; 
	 

	
	@PostConstruct
	public void init() {
		refresh();
		
	}
	
	public void refresh(){
		listOfAssets=assetDataFacede.getAll();
		System.out.println("Ahmed Dakrory: "+listOfAssets.size());
		System.out.println("Ahmed Dakrory: "+listOfAssets.get(0).getId());
	}

	public List<asset> getListOfAssets() {
		return listOfAssets;
	}

	public void setListOfAssets(List<asset> listOfAssets) {
		this.listOfAssets = listOfAssets;
	}

	public assetAppServiceImpl getAssetDataFacede() {
		return assetDataFacede;
	}

	public void setAssetDataFacede(assetAppServiceImpl assetDataFacede) {
		this.assetDataFacede = assetDataFacede;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	 public void onRowEdit(RowEditEvent event) {
	        FacesMessage msg = new FacesMessage("Asset Edited", String.valueOf(((asset) event.getObject()).getId()));
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	    }
	     
	    public void onRowCancel(RowEditEvent event) {
	        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(((asset) event.getObject()).getId()));
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	    }

	
}
