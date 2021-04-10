package br.unitins.petshop.application;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class Util {
	
	public static void addErrorMessage(String msg) {
		addMessage(msg, FacesMessage.SEVERITY_ERROR, null);
	}
	
	public static void addErrorMessage(String msg, String clientId) {
		addMessage(msg, FacesMessage.SEVERITY_ERROR, clientId);
	}
	
	public static void addInfoMessage(String msg) {
		addMessage(msg, FacesMessage.SEVERITY_INFO, null);
	}
	
	public static void addWarnMessage(String msg) {
		addMessage(msg, FacesMessage.SEVERITY_WARN, null);
	}

	private static void addMessage(String msg, Severity severity, String clientId) {
		FacesContext.getCurrentInstance().
		addMessage(clientId, 
				new FacesMessage(severity, msg, null));
	}

}
