package com.tk20.jtlresults;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class FileUploadForm extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String successfull;
	private FormFile uploadfile;
	

	public FormFile getUploadfile() {
		return uploadfile;
	}

	public void setUploadfile(FormFile uploadfile) {

		this.uploadfile = uploadfile;
	}

	public String getSuccessfull() {
		return successfull;
	}

	public void setSuccessfull(String successfull) {
		this.successfull = successfull;
	}
}
