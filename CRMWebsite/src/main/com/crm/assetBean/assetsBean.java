package main.com.crm.assetBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

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
	 

	private asset selectedAsset;
	private UploadedFile file;
	 
	@PostConstruct
	public void init() {
		refresh();
		
	}
	
	public void refresh(){
		listOfAssets=assetDataFacede.getAll();
		System.out.println("Ahmed Dakrory: "+listOfAssets.size());
		System.out.println("Ahmed Dakrory: "+listOfAssets.get(0).getId());
	}

	
	public void selectAsset(int assetId) {
		selectedAsset=assetDataFacede.getById(assetId);
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("/pages/secured/admin/assets/assetDetails.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	 
	    public UploadedFile getFile() {
	        return file;
	    }
	 
	    public void setFile(UploadedFile file) {
	        this.file = file;
	    }
	     
	    public void upload() {
	        if(file != null) {
	            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
	            FacesContext.getCurrentInstance().addMessage(null, message);
	        }
	    }
	     
	    public void handleFileUpload(FileUploadEvent event) {
	        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
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

	public asset getSelectedAsset() {
		return selectedAsset;
	}

	public void setSelectedAsset(asset selectedAsset) {
		this.selectedAsset = selectedAsset;
	}
	
	
	
	
	
}
